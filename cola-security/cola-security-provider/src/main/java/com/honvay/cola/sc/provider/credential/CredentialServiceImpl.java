package com.honvay.cola.sc.provider.credential;

import com.honvay.cola.framework.util.RandomUtils;
import com.honvay.cola.nc.api.NotificationService;
import com.honvay.cola.nc.api.model.Notification;
import com.honvay.cola.sc.api.CredentialService;
import com.honvay.cola.sc.api.model.CredentialConfig;
import com.honvay.cola.sc.api.model.CredentialNotify;
import com.honvay.cola.sc.api.model.CredentialType;
import com.honvay.cola.sc.api.model.CredentialValidation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author LIQIU
 * @date 2018-7-10
 **/
@Slf4j
@Component
public class CredentialServiceImpl implements CredentialService, InitializingBean {
	/**
	 * 验证码缓存名称
	 */
	private static String CREDENTIAL_VALUE_CACHE = "sc_credential_value";

	private static String CREDENTIAL_TOKEN_CACHE = "sc_credential_token";

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private CredentialProperties properties;

	@Autowired(required = false)
	private NotificationService notificationService;

	/**
	 * 生成验证码
	 *
	 * @param request
	 * @return token 验证码令牌
	 */
	@Override
	public String generate(CredentialConfig request) {

		String existsToken = this.getToken(request.getApplication(), request.getPrincipal());

		String token;
		String credential;

		if (StringUtils.isNotEmpty(existsToken)) {

			token = existsToken.split("@")[0];
			credential = this.getCredential(request.getApplication(), request.getPrincipal(), token);
			Long expire = Long.valueOf(existsToken.split("@")[1]);

			this.refreshCache(CREDENTIAL_VALUE_CACHE, this.getCredentialCacheKey(request.getApplication(), request.getPrincipal(), token), expire);
			this.refreshCache(CREDENTIAL_TOKEN_CACHE, this.getTokenCacheKey(request.getApplication(), request.getPrincipal()), expire);

		} else {
			if (CredentialType.NUMBER.equals(request.getType())) {
				credential = RandomUtils.generateNumber(request.getSize());
			} else {
				credential = RandomUtils.generateString(request.getSize());
			}

			token = UUID.randomUUID().toString().replaceAll("-", "");

			//验证码放入缓存
			this.putCache(CREDENTIAL_VALUE_CACHE, this.getCredentialCacheKey(request.getApplication(), request.getPrincipal(), token), credential, request.getExpire());
			this.putCache(CREDENTIAL_TOKEN_CACHE, this.getTokenCacheKey(request.getApplication(), request.getPrincipal()), token + "@" + request.getExpire(), request.getExpire());

		}


		if (request.getNotification() != null) {
			this.notify(credential, request.getNotification());
		}
		return token;
	}

	/**
	 * 刷新Token缓存时间
	 *
	 * @param cacheName 缓存名称
	 * @param key       缓存Key
	 * @param expire    失效时间
	 */
	private void refreshCache(String cacheName, String key, Long expire) {
		this.redisTemplate.expire(cacheName + ":" + key, expire, TimeUnit.MILLISECONDS);
	}

	private String getToken(String application, String principal) {
		return this.getCache(CREDENTIAL_TOKEN_CACHE, this.getTokenCacheKey(application, principal));
	}


	/**
	 * 生成缓存Key
	 *
	 * @param application 应用
	 * @param principal   主体
	 * @param token       令牌
	 * @return 缓存Key
	 */
	private String getCredentialCacheKey(String application, String principal, String token) {
		return new StringBuilder().append(application).append(":")
				.append(principal).append(":")
				.append(token).toString();
	}

	private String getTokenCacheKey(String application, String principal) {
		return application + ":" + principal;
	}

	/**
	 * 通知
	 *
	 * @param credential   凭证
	 * @param notification 通知
	 */
	private void notify(String credential, Notification notification) {
		Map<String, Object> params = Optional.of(notification.getParams()).orElseGet(() -> {
			Map<String, Object> newParams = new HashMap<>(1);
			notification.setParams(newParams);
			return newParams;
		});

		params.put(properties.getCredentialPlaceholder(), credential);
		this.notificationService.notify(notification);
		System.out.println(credential);
	}

	@Override
	public void notify(CredentialNotify notify) {

		String credential = this.getCredential(notify.getApplication(), notify.getPrincipal(), notify.getToken());
		Assert.isTrue(StringUtils.isNotEmpty(credential), "凭证不存在");

		String existsToken = this.getToken(notify.getApplication(), notify.getPrincipal());
		Long expire = Long.valueOf(existsToken.split("@")[1]);

		this.refreshCache(CREDENTIAL_VALUE_CACHE, this.getCredentialCacheKey(notify.getApplication(), notify.getPrincipal(), notify.getToken()), expire);
		this.refreshCache(CREDENTIAL_TOKEN_CACHE, this.getTokenCacheKey(notify.getApplication(), notify.getPrincipal()), expire);

		this.notify(credential, notify.getNotification());

	}

	@Override
	public boolean validate(CredentialValidation validation) {

		String credential = validation.getCredential();

		String value = this.getCache(CREDENTIAL_VALUE_CACHE, this.getCredentialCacheKey(validation.getApplication(), validation.getPrincipal(), validation.getToken()));

		boolean result = validation.getIgnoreCase() != null && validation.getIgnoreCase() ? credential.equalsIgnoreCase(value) : credential.equals(value);

		if (result) {
			//验证通过后需要重新验证
			this.deleteCache(CREDENTIAL_VALUE_CACHE, this.getCredentialCacheKey(validation.getApplication(), validation.getPrincipal(), validation.getToken()));
			this.deleteCache(CREDENTIAL_TOKEN_CACHE, this.getTokenCacheKey(validation.getApplication(), validation.getPrincipal()));
		}

		return result;
	}

	@Override
	public String getCredential(String application, String principal, String token) {
		return this.getCache(CREDENTIAL_VALUE_CACHE, this.getCredentialCacheKey(application, principal, token));
	}


	/**
	 * 放入缓存
	 *
	 * @param cacheName 缓存名称
	 * @param key       缓存key
	 * @param code      验证码
	 * @param expire    失效时间
	 */
	private void putCache(String cacheName, String key, String code, long expire) {
		this.redisTemplate.opsForValue().set(cacheName + ":" + key, code, expire, TimeUnit.MILLISECONDS);
	}

	/**
	 * 删除缓存
	 *
	 * @param cacheName 缓存名称
	 * @param token     令牌
	 */
	private void deleteCache(String cacheName, String token) {
		this.redisTemplate.delete(cacheName + ":" + token);
	}

	/**
	 * 获取缓存
	 *
	 * @param token 令牌
	 */
	private String getCache(String cacheName, String token) {
		return this.redisTemplate.opsForValue().get(cacheName + ":" + token);
	}

	@Override
	public void afterPropertiesSet() {
		//Assert.notNull(notificationService, "Notification Service must not be null");
	}
}

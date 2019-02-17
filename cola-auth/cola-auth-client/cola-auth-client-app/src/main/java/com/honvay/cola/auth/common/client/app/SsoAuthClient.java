package com.honvay.cola.auth.common.client.app;

import com.honvay.cola.auth.client.common.oauth2.ac.AcAccessTokenProvider;
import com.honvay.cola.auth.client.common.oauth2.ac.AcResourceDetails;
import com.honvay.cola.auth.client.common.oauth2.openid.OpenIdAccessTokenProvider;
import com.honvay.cola.auth.client.common.oauth2.openid.OpenIdResourceDetails;
import com.honvay.cola.auth.client.common.oauth2.sms.SmsAccessTokenProvider;
import com.honvay.cola.auth.client.common.oauth2.sms.SmsResourceDetails;
import com.honvay.cola.framework.core.protocol.Result;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author LIQIU
 * created on 2018-11-27
 **/
public class SsoAuthClient {

	private SsoClientProperties properties;


	public SsoAuthClient(SsoClientProperties properties) {
		this.properties = properties;
	}

	/**
	 * 登录
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	public OAuth2AccessToken login(String username, String password) {
		ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
		details.setUsername(username);
		details.setPassword(password);
		details.setAccessTokenUri(this.properties.getAccessTokenUri());
		details.setClientId(this.properties.getClientId());
		details.setClientSecret(this.properties.getClientSecret());
		OAuth2RestTemplate auth2RestTemplate = new OAuth2RestTemplate(details);
		auth2RestTemplate.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());
		return auth2RestTemplate.getAccessToken();
	}

	/**
	 * 退出
	 *
	 * @return
	 */
	public Result<String> logout() {
		OAuth2RestTemplate auth2RestTemplate = new OAuth2RestTemplate(properties);
		auth2RestTemplate.getForObject(properties.getRevokeTokenUri(), String.class);
		return Result.success();
	}

	/**
	 * openId登录
	 *
	 * @param openId   openid
	 * @param provider 供应商
	 * @return
	 */
	public OAuth2AccessToken loginByOpenId(String openId, String provider) {
		OpenIdResourceDetails details = new OpenIdResourceDetails();
		details.setOpenId(openId);
		details.setProvider(provider);
		details.setAccessTokenUri(this.properties.getAccessTokenUri());
		details.setClientId(this.properties.getClientId());
		details.setClientSecret(this.properties.getClientSecret());
		OAuth2RestTemplate auth2RestTemplate = new OAuth2RestTemplate(details);
		auth2RestTemplate.setAccessTokenProvider(new OpenIdAccessTokenProvider());
		return auth2RestTemplate.getAccessToken();
	}

	/**
	 * 短信登录
	 *
	 * @param phoneNumber 手机号码
	 * @param credential  验证码
	 * @return
	 */
	public OAuth2AccessToken loginBySms(String phoneNumber, String credential) {
		SmsResourceDetails details = new SmsResourceDetails();
		details.setPhoneNumber(phoneNumber);
		details.setCredential(credential);
		details.setAccessTokenUri(this.properties.getAccessTokenUri());
		details.setClientId(this.properties.getClientId());
		details.setClientSecret(this.properties.getClientSecret());
		OAuth2RestTemplate auth2RestTemplate = new OAuth2RestTemplate(details);
		auth2RestTemplate.setAccessTokenProvider(new SmsAccessTokenProvider());
		return auth2RestTemplate.getAccessToken();
	}

	/**
	 * 授权码登录
	 *
	 * @param authorizationCode 授权码
	 * @param provider          第三方
	 * @return
	 */
	public OAuth2AccessToken loginByAuthorizationCode(String authorizationCode, String provider) {
		AcResourceDetails details = new AcResourceDetails();
		details.setProvider(provider);
		details.setAuthorizationCode(authorizationCode);
		details.setAccessTokenUri(this.properties.getAccessTokenUri());
		details.setClientId(this.properties.getClientId());
		details.setClientSecret(this.properties.getClientSecret());
		OAuth2RestTemplate auth2RestTemplate = new OAuth2RestTemplate(details);
		auth2RestTemplate.setAccessTokenProvider(new AcAccessTokenProvider());
		return auth2RestTemplate.getAccessToken();
	}
}

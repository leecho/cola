package com.honvay.cola.auth.social.qq.api.impl;

import com.honvay.cola.auth.social.qq.api.QQ;
import com.honvay.cola.auth.social.qq.api.QQUserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @author LIQIU
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

	//http://wiki.connect.qq.com/openapi%E8%B0%83%E7%94%A8%E8%AF%B4%E6%98%8E_oauth2-0
	private static final String QQ_URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
	//http://wiki.connect.qq.com/get_user_info(access_token由父类提供)
	private static final String QQ_URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
	/**
	 * appId 配置文件读取
	 */
	private String appId;
	/**
	 * openid 请求QQ_URL_GET_OPENID返回
	 */
	private String openId;
	/**
	 * 工具类
	 */
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 构造方法获取openId
	 */
	public QQImpl(String accessToken, String appId) {
		//access_token作为查询参数来携带。
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

		this.appId = appId;

		String url = String.format(QQ_URL_GET_OPENID, accessToken);
		String result = getRestTemplate().getForObject(url, String.class);

		log.info("【QQImpl】 QQ_URL_GET_OPENID={} result={}", url, result);

		this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
	}

	@Override
	public QQUserInfo getUserInfo() {
		String url = String.format(QQ_URL_GET_USER_INFO, appId, openId);
		String result = getRestTemplate().getForObject(url, String.class);

		log.info("【QQImpl】 QQ_URL_GET_USER_INFO={} result={}", url, result);

		QQUserInfo userInfo;
		try {
			userInfo = objectMapper.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openId);
			return userInfo;
		} catch (Exception e) {
			throw new RuntimeException("获取用户信息失败", e);
		}
	}
}
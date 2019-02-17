package com.honvay.cola.auth.social.alipay.api.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.honvay.cola.auth.social.alipay.AlipayProperties;
import com.honvay.cola.auth.social.alipay.api.Alipay;
import com.honvay.cola.auth.social.alipay.api.AlipayUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.ApiBinding;

/**
 * @author LIQIU
 */
@Slf4j
public class AlipayImpl implements ApiBinding, Alipay {

	/**
	 * appId 配置文件读取
	 */
	private String appId;
	/**
	 * openid 请求QQ_URL_GET_OPENID返回
	 */

	private String accessToken;

	private AlipayProperties properties;


	/**
	 * 构造方法获取openId
	 */
	public AlipayImpl(String accessToken,AlipayProperties properties) {
		//access_token作为查询参数来携带。
		this.appId = properties.getAppId();
		this.accessToken = accessToken;
		this.properties = properties;
	}

	@Override
	public AlipayUserInfo getUserInfo() {

		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, properties.getPrivateKey(), properties.getFormat(), properties.getCharset(), properties.getPublicKey(), properties.getSignType());
		AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
		AlipayUserInfoShareResponse response = null;
		try {
			response = alipayClient.execute(request, this.accessToken);
			if (response.isSuccess()) {
				AlipayUserInfo alipayUserInfo = new AlipayUserInfo();
				alipayUserInfo.setAvatar(response.getAvatar());
				alipayUserInfo.setNickName(response.getNickName());
				alipayUserInfo.setUserId(response.getUserId());
				return alipayUserInfo;
			} else {
				throw new IllegalArgumentException(response.getMsg());
			}

		} catch (AlipayApiException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	@Override
	public boolean isAuthorized() {
		return this.accessToken != null;
	}
}
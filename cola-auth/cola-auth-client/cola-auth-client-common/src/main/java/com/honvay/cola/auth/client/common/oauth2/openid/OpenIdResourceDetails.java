package com.honvay.cola.auth.client.common.oauth2.openid;

import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;

/**
 * @author LIQIU
 * created on 2018-11-27
 **/
public class OpenIdResourceDetails extends BaseOAuth2ProtectedResourceDetails {

	private String openId;

	private String provider;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}

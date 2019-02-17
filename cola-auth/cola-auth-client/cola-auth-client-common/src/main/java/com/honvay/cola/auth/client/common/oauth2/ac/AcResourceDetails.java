package com.honvay.cola.auth.client.common.oauth2.ac;

import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;

/**
 * @author LIQIU
 * created on 2019/1/15
 **/
public class AcResourceDetails  extends BaseOAuth2ProtectedResourceDetails {

	private String authorizationCode;

	private String provider;

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}

package com.honvay.cola.auth.common.client.app;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;

/**
 * @author LIQIU
 * created on 2018-11-27
 **/
@ConfigurationProperties(prefix = "security.oauth2.client")
public class SsoClientProperties extends BaseOAuth2ProtectedResourceDetails {

	/**
	 * 删除token URL
	 */
	private String revokeTokenUri;

	public String getRevokeTokenUri() {
		return revokeTokenUri;
	}

	public void setRevokeTokenUri(String revokeTokenUri) {
		this.revokeTokenUri = revokeTokenUri;
	}
}

package com.honvay.cola.auth.client.common.oauth2.sms;

import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;

/**
 * @author LIQIU
 * created on 2018-11-27
 **/
public class SmsResourceDetails extends BaseOAuth2ProtectedResourceDetails {

	private String phoneNumber;

	private String credential;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}
}

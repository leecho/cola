package com.honvay.cola.auth.client.common.oauth2.sms;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.OAuth2AccessTokenSupport;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Iterator;
import java.util.List;

/**
 * @author LIQIU
 * created on 2018-11-26
 **/
public class SmsAccessTokenProvider  extends OAuth2AccessTokenSupport implements AccessTokenProvider {


	@Override
	public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest request) throws UserRedirectRequiredException, UserApprovalRequiredException, AccessDeniedException {
		SmsResourceDetails resource = (SmsResourceDetails) details;
		return retrieveToken(request, resource, getParametersForTokenRequest(resource, request), new HttpHeaders());
	}

	@Override
	public boolean supportsResource(OAuth2ProtectedResourceDetails resource) {
		return resource.getClass().equals(SmsResourceDetails.class);
	}

	@Override
	public OAuth2AccessToken refreshAccessToken(OAuth2ProtectedResourceDetails resource, OAuth2RefreshToken refreshToken, AccessTokenRequest request) throws UserRedirectRequiredException {
		return null;
	}

	@Override
	public boolean supportsRefresh(OAuth2ProtectedResourceDetails resource) {
		return supportsResource(resource);
	}

	private MultiValueMap<String, String> getParametersForTokenRequest(SmsResourceDetails resource, AccessTokenRequest request) {

		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.set("grant_type", "sms");
		form.set("phoneNumber", resource.getPhoneNumber());
		form.set("credential", resource.getCredential());
		form.putAll(request);

		if (resource.isScoped()) {

			StringBuilder builder = new StringBuilder();
			List<String> scope = resource.getScope();

			if (scope != null) {
				Iterator<String> scopeIt = scope.iterator();
				while (scopeIt.hasNext()) {
					builder.append(scopeIt.next());
					if (scopeIt.hasNext()) {
						builder.append(' ');
					}
				}
			}

			form.set("scope", builder.toString());
		}

		return form;

	}
}

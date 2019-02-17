package com.honvay.cola.auth.openid;

import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LIQIU
 * created on 2018-11-20
 **/
@Order(3)
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String OPENID_KEY = "openid";
	public static final String PROVIDER_KEY = "provider";
	private String openIdParameter = OPENID_KEY;
	private String providerParameter = PROVIDER_KEY;
	private boolean postOnly = true;

	public OpenIdAuthenticationFilter() {
		super(new AntPathRequestMatcher("/loginByOpenId", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (this.postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		} else {

			String openId = this.obtainOpenId(request);
			if (openId == null) {
				openId = "";
			}

			openId = openId.trim();

			String provider = this.obtainProvider(request);
			if (provider == null) {
				provider = "";
			}

			provider = provider.trim();

			OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openId, provider);
			this.setDetails(request, authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);
		}
	}

	/**
	 * 获取OpenId
	 */
	protected String obtainOpenId(HttpServletRequest request) {
		return request.getParameter(this.openIdParameter);
	}

	/**
	 * 获取Provider
	 */
	protected String obtainProvider(HttpServletRequest request) {
		return request.getParameter(this.providerParameter);
	}

	protected void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from the login
	 * request.
	 *
	 * @param openIdParameter the parameter name. Defaults to "username".
	 */
	public void setOpenIdParameter(String openIdParameter) {
		Assert.hasText(openIdParameter, "Username parameter must not be empty or null");
		this.openIdParameter = openIdParameter;
	}

	/**
	 * Sets the parameter name which will be used to obtain the password from the login
	 * request..
	 *
	 * @param providerParameter the parameter name. Defaults to "password".
	 */
	public void setProviderParameter(String providerParameter) {
		Assert.hasText(providerParameter, "Password parameter must not be empty or null");
		this.providerParameter = providerParameter;
	}

}

package com.honvay.cola.auth.ac;

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
public class AcAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String AC_KEY = "authorizationCode";
	public static final String PROVIDER_KEY = "provider";
	private String authorizationCodeParameter = AC_KEY;
	private String providerParameter = PROVIDER_KEY;
	private boolean postOnly = true;

	public AcAuthenticationFilter() {
		super(new AntPathRequestMatcher("/loginByAuthorizationCode", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (this.postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		} else {

			String authorizationCode = this.obtainAuthorizationCode(request);
			if (authorizationCode == null) {
				authorizationCode = "";
			}

			authorizationCode = authorizationCode.trim();

			String provider = this.obtainProvider(request);
			if (provider == null) {
				provider = "";
			}

			provider = provider.trim();

			AcAuthenticationToken authRequest = new AcAuthenticationToken(authorizationCode, provider);
			this.setDetails(request, authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);
		}
	}

	/**
	 * 获取OpenId
	 */
	protected String obtainAuthorizationCode(HttpServletRequest request) {
		return request.getParameter(this.authorizationCodeParameter);
	}

	/**
	 * 获取Provider
	 */
	protected String obtainProvider(HttpServletRequest request) {
		return request.getParameter(this.providerParameter);
	}

	protected void setDetails(HttpServletRequest request, AcAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from the login
	 * request.
	 *
	 * @param authorizationCodeParameter the parameter name. Defaults to "username".
	 */
	public void setAuthorizationCodeParameter(String authorizationCodeParameter) {
		Assert.hasText(authorizationCodeParameter, "Username parameter must not be empty or null");
		this.authorizationCodeParameter = authorizationCodeParameter;
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

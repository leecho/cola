package com.honvay.cola.auth.web.sms.authentication;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LIQIU
 * created on 2018-11-20
 **/
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String PHONE_NUMBER_KEY = "phoneNumber";
	public static final String SMS_CREDENTIAL_KEY = "credential";
	public static final String SMS_TOKEN_SESSION_KEY = "sms_credential_token";
	private String phoneNumberParameter = PHONE_NUMBER_KEY;
	private String credentialParameter = SMS_CREDENTIAL_KEY;
	private boolean postOnly = true;

	public SmsAuthenticationFilter() {
		super(new AntPathRequestMatcher("/loginBySms", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (this.postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		} else {

			String phoneNumber = this.obtainPhoneNumber(request);
			if (phoneNumber == null) {
				phoneNumber = "";
			}

			phoneNumber = phoneNumber.trim();

			String credential = this.obtainCredential(request);
			if (credential == null) {
				credential = "";
			}

			credential = credential.trim();

			String token = (String) request.getSession().getAttribute(SMS_TOKEN_SESSION_KEY);

			SmsAuthenticationToken authRequest = new SmsAuthenticationToken(phoneNumber, credential, token);
			this.setDetails(request, authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);
		}
	}

	/**
	 * 获取手机号
	 */
	protected String obtainPhoneNumber(HttpServletRequest request) {
		return request.getParameter(this.phoneNumberParameter);
	}

	/**
	 * 获取手机号
	 */
	protected String obtainCredential(HttpServletRequest request) {
		return request.getParameter(this.credentialParameter);
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from the login
	 * request.
	 *
	 * @param phoneNumberParameter the parameter name. Defaults to "username".
	 */
	public void setPhoneNumberParameter(String phoneNumberParameter) {
		Assert.hasText(phoneNumberParameter, "Username parameter must not be empty or null");
		this.phoneNumberParameter = phoneNumberParameter;
	}

	/**
	 * Sets the parameter name which will be used to obtain the password from the login
	 * request..
	 *
	 * @param credentialParameter the parameter name. Defaults to "password".
	 */
	public void setCredentialParameter(String credentialParameter) {
		Assert.hasText(credentialParameter, "Password parameter must not be empty or null");
		this.credentialParameter = credentialParameter;
	}


	protected void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}

	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return this.getFailureHandler();
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
}

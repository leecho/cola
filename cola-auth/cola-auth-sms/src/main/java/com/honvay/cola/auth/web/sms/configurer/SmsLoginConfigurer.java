package com.honvay.cola.auth.web.sms.configurer;

import com.honvay.cola.auth.web.sms.authentication.SmsAuthenticationFilter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author LIQIU
 * created on 2018/12/27
 **/
public class SmsLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractAuthenticationFilterConfigurer<H, SmsLoginConfigurer<H>, SmsAuthenticationFilter> {

	public SmsLoginConfigurer() {
		super(new SmsAuthenticationFilter(), "/loginBySms");
	}

	public SmsLoginConfigurer<H> phoneNumberParameter(String phoneNumberParameter) {
		getAuthenticationFilter().setPhoneNumberParameter(phoneNumberParameter);
		return this;
	}

	public SmsLoginConfigurer<H> eventPublisher(ApplicationEventPublisher eventPublisher) {
		getAuthenticationFilter().setApplicationEventPublisher(eventPublisher);
		return this;
	}


	@Override
	public void configure(H http) throws Exception {
		try {
			super.configure(http);
		} catch (IllegalArgumentException e) {
			http.addFilterBefore(this.getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		}
	}

	/**
	 * The HTTP parameter to look for the password when performing authentication. Default
	 * is "password".
	 *
	 * @param credentialParameter the HTTP parameter to look for the password when
	 *                            performing authentication
	 * @return the {@link SmsLoginConfigurer} for additional customization
	 */
	public SmsLoginConfigurer<H> credentialParameter(String credentialParameter) {
		getAuthenticationFilter().setCredentialParameter(credentialParameter);
		return this;
	}

	@Override
	protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
		return new AntPathRequestMatcher(loginProcessingUrl, "POST");
	}
}

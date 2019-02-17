package com.honvay.cola.auth.ac.configrer;

import com.honvay.cola.auth.ac.AcAuthenticationFilter;
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
public class AcLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractAuthenticationFilterConfigurer<H, AcLoginConfigurer<H>, AcAuthenticationFilter> {

	public AcLoginConfigurer() {
		super(new AcAuthenticationFilter(), "/loginByAuthorizationCode");
	}

	public AcLoginConfigurer<H> authorizationCodeParameter(String openIdParameter) {
		getAuthenticationFilter().setAuthorizationCodeParameter(openIdParameter);
		return this;
	}

	public AcLoginConfigurer<H> eventPublisher(ApplicationEventPublisher eventPublisher) {
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
	 * @param providerParameter the HTTP parameter to look for the password when
	 *                          performing authentication
	 * @return the {@link AcLoginConfigurer} for additional customization
	 */
	public AcLoginConfigurer<H> providerParameter(String providerParameter) {
		getAuthenticationFilter().setProviderParameter(providerParameter);
		return this;
	}

	@Override
	protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
		return new AntPathRequestMatcher(loginProcessingUrl, "POST");
	}
}

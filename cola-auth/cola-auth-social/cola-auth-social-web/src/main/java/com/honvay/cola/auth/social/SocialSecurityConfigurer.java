package com.honvay.cola.auth.social;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author LIQIU
 * created on 2018-11-14
 **/
public class SocialSecurityConfigurer extends SpringSocialConfigurer{

	private String filterProcessesUrl;

	public SocialSecurityConfigurer(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		String permitUrl = filterProcessesUrl;
		if (!permitUrl.endsWith("/")) {
			permitUrl += "/";
		}
		permitUrl += "**";
		http.authorizeRequests().antMatchers(permitUrl).permitAll();
		super.configure(http);
	}

	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
		filter.setFilterProcessesUrl(filterProcessesUrl);
		return (T) filter;
	}
}

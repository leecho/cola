package com.honvay.cola.auth.web.configuration;

import com.honvay.cola.auth.captcha.filter.CaptchaAuthenticationFilter;
import com.honvay.cola.auth.channel.config.ChannelSecurityConfigurer;
import com.honvay.cola.auth.web.handler.WebAuthenticationFailureHandler;
import com.honvay.cola.auth.web.handler.WebAuthenticationSuccessHandler;
import com.honvay.cola.auth.web.sms.SmsCredentialProperties;
import com.honvay.cola.auth.web.sms.configurer.SmsLoginConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author LIQIU
 */

@Configuration
public class WebSecurityConfiguration implements ChannelSecurityConfigurer {

	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private SessionAuthenticationStrategy sessionAuthenticationStrategy;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	private CaptchaAuthenticationFilter captchaAuthenticationFilter = new CaptchaAuthenticationFilter();

	@Override
	public void configure(WebSecurity web) {

	}

	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new WebAuthenticationFailureHandler("/login?error");
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new WebAuthenticationSuccessHandler();
	}

	@Bean
	public SmsLoginConfigurer smsLoginConfigurer(SmsCredentialProperties properties) {

		AuthenticationFailureHandler failureHandler = new ForwardAuthenticationFailureHandler(properties.getLoginFailureUrl());

		SmsLoginConfigurer<HttpSecurity> configurer = new SmsLoginConfigurer<>();
		configurer.successHandler(this.successHandler())
				.failureHandler(failureHandler)
				.eventPublisher(applicationEventPublisher)
				.permitAll();

		//拦截登录验证码
		captchaAuthenticationFilter.addRequestMatcher(new AntPathRequestMatcher(properties.getLoginProcessUrl(), HttpMethod.POST.name()), failureHandler);

		return configurer;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {


		captchaAuthenticationFilter.addRequestMatcher(new AntPathRequestMatcher("/login", HttpMethod.POST.name()), this.failureHandler());

		http.setSharedObject(CaptchaAuthenticationFilter.class, captchaAuthenticationFilter);

		http.authorizeRequests()
				.antMatchers("/login", "/logout", "/error").permitAll()
				.antMatchers("/captcha", "/session-invalid").permitAll()
				.and()
				.formLogin()
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.failureHandler(this.failureHandler())
				.successHandler(this.successHandler())
				//.failureHandler(new WebAuthenticationFailureHandler())
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(false)
				.and()
				.addFilterBefore(captchaAuthenticationFilter, AbstractPreAuthenticatedProcessingFilter.class)
				.sessionManagement()
				.invalidSessionUrl("/session-invalid")
				.maximumSessions(1)
				.expiredUrl("/session-invalid")
				.sessionRegistry(sessionRegistry)
				.and()
				.sessionFixation()
				.migrateSession()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.sessionAuthenticationStrategy(sessionAuthenticationStrategy);
	}

	@Override
	public int getOrder() {
		return 0;
	}
}

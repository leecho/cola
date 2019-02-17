package com.honvay.cola.auth.jwt.openid;

import com.honvay.cola.auth.openid.configrer.OpenIdLoginConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author LIQIU
 * created on 2018/12/29
 **/
@Configuration
public class OpenIdJwtConfiguration {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Bean
	public OpenIdLoginConfigurer<HttpSecurity> openIDLoginConfigurer(AuthenticationSuccessHandler successHandler,
																	 AuthenticationFailureHandler failureHandler) {
		OpenIdLoginConfigurer<HttpSecurity> configurer = new OpenIdLoginConfigurer<>();
		configurer.successHandler(successHandler)
				.failureHandler(failureHandler)
				.eventPublisher(applicationEventPublisher)
				.permitAll();
		return configurer;
	}


}

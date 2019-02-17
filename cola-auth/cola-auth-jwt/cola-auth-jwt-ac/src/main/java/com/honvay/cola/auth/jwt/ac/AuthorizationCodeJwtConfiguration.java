package com.honvay.cola.auth.jwt.ac;

import com.honvay.cola.auth.ac.configrer.AcLoginConfigurer;
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
public class AuthorizationCodeJwtConfiguration {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Bean
	public AcLoginConfigurer<HttpSecurity> acLoginConfigurer(AuthenticationSuccessHandler successHandler,
															 AuthenticationFailureHandler failureHandler) {
		AcLoginConfigurer<HttpSecurity> configurer = new AcLoginConfigurer<>();
		configurer.successHandler(successHandler)
				.failureHandler(failureHandler)
				.eventPublisher(applicationEventPublisher)
				.permitAll();
		return configurer;
	}

}

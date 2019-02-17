package com.honvay.cola.auth.jwt.sms;

import com.honvay.cola.auth.web.sms.configurer.SmsLoginConfigurer;
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
public class SmsJwtConfiguration {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Bean
	public SmsLoginConfigurer<HttpSecurity> smsLoginConfigurer(AuthenticationSuccessHandler successHandler,
															   AuthenticationFailureHandler failureHandler) {
		SmsLoginConfigurer<HttpSecurity> configurer = new SmsLoginConfigurer<>();
		configurer.successHandler(successHandler)
				.failureHandler(failureHandler)
				.eventPublisher(applicationEventPublisher)
				.permitAll();
		return configurer;
	}

}

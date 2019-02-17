package com.honvay.cola.auth.web.sms;

import com.honvay.cola.auth.web.sms.authentication.SmsAuthenticationProvider;
import com.honvay.cola.auth.web.sms.configurer.SmsChannelSecurityConfigurer;
import com.honvay.cola.auth.web.sms.configurer.SmsLoginConfigurer;
import com.honvay.cola.auth.web.sms.userdetails.SmsUserDetailsServiceImpl;
import com.honvay.cola.sc.api.CredentialService;
import com.honvay.cola.uc.api.UserService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LIQIU
 * created on 2018/12/26
 **/
@Configuration
@EnableConfigurationProperties(SmsCredentialProperties.class)
public class SmsAuthenticationConfiguration {

	@Bean
	public SmsUserDetailsService smsUserDetailsService(UserService userService) {
		return new SmsUserDetailsServiceImpl(userService);
	}

	@Bean
	public SmsAuthenticationProvider smsAuthenticationProvider(SmsUserDetailsService userDetailsService,
															   CredentialService credentialService) {
		return new SmsAuthenticationProvider(userDetailsService, credentialService);
	}

	@Bean
	public SmsChannelSecurityConfigurer smsChannelSecurityConfigurer(SmsLoginConfigurer smsLoginConfigurer,
																	 SmsAuthenticationProvider smsAuthenticationProvider,
																	 SmsCredentialProperties smsCredentialProperties) {
		return new SmsChannelSecurityConfigurer(smsLoginConfigurer, smsAuthenticationProvider, smsCredentialProperties);
	}


}

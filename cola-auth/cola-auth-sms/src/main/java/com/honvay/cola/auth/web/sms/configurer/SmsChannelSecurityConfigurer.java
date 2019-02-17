package com.honvay.cola.auth.web.sms.configurer;

import com.honvay.cola.auth.channel.config.AbstractChannelSecurityConfigurer;
import com.honvay.cola.auth.web.sms.SmsCredentialProperties;
import com.honvay.cola.auth.web.sms.authentication.SmsAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * @author LIQIU
 * created on 2018/12/23
 **/
public class SmsChannelSecurityConfigurer extends AbstractChannelSecurityConfigurer {

	private SmsAuthenticationProvider smsAuthenticationProvider;
	private SmsCredentialProperties smsCredentialProperties;

	public SmsChannelSecurityConfigurer(SmsLoginConfigurer configurer,
										SmsAuthenticationProvider smsAuthenticationProvider,
										SmsCredentialProperties smsCredentialProperties) {
		super(configurer);
		this.smsAuthenticationProvider = smsAuthenticationProvider;
		this.smsCredentialProperties = smsCredentialProperties;
	}

	@Override
	public void config(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authenticationProvider(smsAuthenticationProvider);
		httpSecurity.authorizeRequests().antMatchers(smsCredentialProperties.getLoginProcessUrl(),
				smsCredentialProperties.getSendSmsUrl()).permitAll();
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {

	}

	@Override
	public int getOrder() {
		return 1;
	}
}

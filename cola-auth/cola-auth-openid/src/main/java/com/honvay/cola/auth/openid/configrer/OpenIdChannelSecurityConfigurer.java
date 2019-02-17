package com.honvay.cola.auth.openid.configrer;

import com.honvay.cola.auth.channel.config.AbstractChannelSecurityConfigurer;
import com.honvay.cola.auth.openid.OpenIdProperties;
import com.honvay.cola.auth.openid.provider.OpenIdAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * @author LIQIU
 * created on 2018/12/23
 **/
public class OpenIdChannelSecurityConfigurer extends AbstractChannelSecurityConfigurer {

	private OpenIdAuthenticationProvider openIdAuthenticationProvider;
	private OpenIdProperties openIdProperties;

	public OpenIdChannelSecurityConfigurer(OpenIdLoginConfigurer adapter,
										   OpenIdAuthenticationProvider openIdAuthenticationProvider,
										   OpenIdProperties openIdProperties) {
		super(adapter);
		this.openIdAuthenticationProvider = openIdAuthenticationProvider;
		this.openIdProperties = openIdProperties;
	}

	@Override
	public void config(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authenticationProvider(openIdAuthenticationProvider);
		httpSecurity.authorizeRequests().antMatchers(openIdProperties.getLoginProcessUrl()).permitAll();
	}

	@Override
	public void configure(WebSecurity webSecurity) {

	}
}

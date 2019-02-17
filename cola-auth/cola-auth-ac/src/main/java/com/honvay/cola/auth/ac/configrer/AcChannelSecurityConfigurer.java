package com.honvay.cola.auth.ac.configrer;

import com.honvay.cola.auth.ac.AcProperties;
import com.honvay.cola.auth.ac.provider.AcAuthenticationProvider;
import com.honvay.cola.auth.channel.config.AbstractChannelSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * @author LIQIU
 * created on 2018/12/23
 **/
public class AcChannelSecurityConfigurer extends AbstractChannelSecurityConfigurer {

	private AcAuthenticationProvider acAuthenticationProvider;
	private AcProperties acProperties;

	public AcChannelSecurityConfigurer(AcLoginConfigurer adapter,
									   AcAuthenticationProvider acAuthenticationProvider,
									   AcProperties acProperties) {
		super(adapter);
		this.acAuthenticationProvider = acAuthenticationProvider;
		this.acProperties = acProperties;
	}

	@Override
	public void config(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authenticationProvider(acAuthenticationProvider);
		httpSecurity.authorizeRequests().antMatchers(acProperties.getLoginProcessUrl()).permitAll();
	}

	@Override
	public void configure(WebSecurity webSecurity) {

	}
}

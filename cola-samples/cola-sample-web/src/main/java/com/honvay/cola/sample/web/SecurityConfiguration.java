package com.honvay.cola.sample.web;

import com.honvay.cola.auth.channel.config.ChannelSecurityConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.stereotype.Component;

/**
 * @author LIQIU
 * created on 2018/12/26
 **/
@Component
@Order(101)
public class SecurityConfiguration implements ChannelSecurityConfigurer {

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/webjars/**", "/resources/**", "/favicon.ico");
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers("/signin/**").permitAll();
	}

	@Override
	public int getOrder() {
		return 2;
	}
}

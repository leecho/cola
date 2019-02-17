package com.honvay.cola.auth.channel.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * @author LIQIU
 * created on 2018/12/23
 **/
public abstract class AbstractChannelSecurityConfigurer implements ChannelSecurityConfigurer {

	private SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> adapter;

	protected AbstractChannelSecurityConfigurer(SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> adapter) {
		this.adapter = adapter;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		if (adapter != null) {
			http.apply(adapter);
		}
		config(http);
	}

	/**
	 * 立即配置Security
	 *
	 * @param http
	 * @throws Exception
	 */
	public abstract void config(HttpSecurity http) throws Exception;

	@Override
	public int getOrder() {
		return 100;
	}
}

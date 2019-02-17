package com.honvay.cola.auth.channel.config;

import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * @author LIQIU
 * created on 2018/12/23
 **/
public interface ChannelSecurityConfigurer extends Ordered {

	/**
	 * 配置HttpSecurity，会立即配置
	 *
	 * @param httpSecurity
	 * @throws Exception
	 */
	default void configure(HttpSecurity httpSecurity) throws Exception {

	}

	/**
	 * 配置HttpSecurity，会立即配置
	 *
	 * @param webSecurity
	 * @throws Exception
	 */
	default void configure(WebSecurity webSecurity) throws Exception {

	}
}

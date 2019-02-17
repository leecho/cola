package com.honvay.cola.auth.social;

import com.honvay.cola.auth.channel.config.AbstractChannelSecurityConfigurer;
import com.honvay.cola.auth.social.core.SocialProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author LIQIU
 * created on 2018/12/23
 **/
public class SocialChannelSecurityConfigurer extends AbstractChannelSecurityConfigurer {

	private SocialProperties socialProperties;


	public SocialChannelSecurityConfigurer(SocialProperties socialProperties, SpringSocialConfigurer springSocialConfigurer) {
		super(springSocialConfigurer);
		this.socialProperties = socialProperties;
	}

	@Override
	public void config(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers(socialProperties.getSignupProcessUrl()).permitAll();
	}

	@Override
	public void configure(WebSecurity webSecurity) {
	}
}

package com.honvay.cola.auth.social;

import com.honvay.cola.auth.social.core.SocialProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author LIQIU
 */
@Configuration
@EnableConfigurationProperties(SocialProperties.class)
public class SocialWebAutoConfiguration extends SocialConfigurerAdapter {

	/**
	 * 社交登录配类
	 *
	 * @return
	 */
	@Bean
	public SpringSocialConfigurer socialConfigurer(SocialProperties socialProperties) {
		SocialSecurityConfigurer configurer = new SocialSecurityConfigurer(socialProperties.getLoginProcessUrl());
		configurer.signupUrl(socialProperties.getSignupUrl());
		return configurer;
	}

	@Bean
	public SocialChannelSecurityConfigurer socialChannelSecurityConfigurer(SpringSocialConfigurer socialConfigurer,
																		   SocialProperties socialProperties) {
		return new SocialChannelSecurityConfigurer(socialProperties, socialConfigurer);
	}


	/**
	 * 处理注册流程的工具类
	 *
	 * @param factoryLocator
	 * @return
	 */
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator factoryLocator) {
		return new ProviderSignInUtils(factoryLocator, getUsersConnectionRepository(factoryLocator));
	}

}
package com.honvay.cola.auth.openid;

import com.honvay.cola.auth.openid.configrer.OpenIdChannelSecurityConfigurer;
import com.honvay.cola.auth.openid.configrer.OpenIdLoginConfigurer;
import com.honvay.cola.auth.openid.provider.OpenIdAuthenticationProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @author LIQIU
 * created on 2018-11-27
 **/
@Configuration
@EnableConfigurationProperties(OpenIdProperties.class)
public class OpenIdAuthenticationConfiguration {

	@Bean
	public OpenIdAuthenticationProvider openIdAuthenticationProvider(SocialUserDetailsService socialUserDetailsService,
																	 UsersConnectionRepository usersConnectionRepository) {
		return new OpenIdAuthenticationProvider(socialUserDetailsService, usersConnectionRepository);
	}


	@Bean
	@ConditionalOnBean(OpenIdLoginConfigurer.class)
	public OpenIdChannelSecurityConfigurer openIdChannelSecurityConfigurer(OpenIdLoginConfigurer openIdLoginConfigurer,
																		   OpenIdAuthenticationProvider openIdAuthenticationProvider,
																		   OpenIdProperties openIdProperties) {
		return new OpenIdChannelSecurityConfigurer(openIdLoginConfigurer,openIdAuthenticationProvider,openIdProperties);
	}
}

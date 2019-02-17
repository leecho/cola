package com.honvay.cola.auth.ac;

import com.honvay.cola.auth.ac.configrer.AcChannelSecurityConfigurer;
import com.honvay.cola.auth.ac.configrer.AcLoginConfigurer;
import com.honvay.cola.auth.ac.provider.AcAuthenticationProvider;
import com.honvay.cola.auth.channel.config.ChannelSecurityConfigurer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @author LIQIU
 * created on 2018-11-27
 **/
@Configuration
@EnableConfigurationProperties(AcProperties.class)
@AutoConfigureAfter(ChannelSecurityConfigurer.class)
public class AcAuthenticationConfiguration {

	@Bean
	public AcAuthenticationProvider acAuthenticationProvider(SocialUserDetailsService socialUserDetailsService,
															 ConnectionFactoryLocator connectionFactoryLocator,
															 UsersConnectionRepository usersConnectionRepository) {
		return new AcAuthenticationProvider(socialUserDetailsService, connectionFactoryLocator, usersConnectionRepository);
	}


	@Bean
	public AcChannelSecurityConfigurer acChannelSecurityConfigurer(ObjectProvider<AcLoginConfigurer> openIdLoginConfigurerProvider,
																   AcAuthenticationProvider openIdAuthenticationProvider,
																   AcProperties acProperties) {
		return new AcChannelSecurityConfigurer(openIdLoginConfigurerProvider.getIfAvailable(), openIdAuthenticationProvider, acProperties);
	}
}

package com.honvay.cola.auth.social.core;

import com.honvay.cola.auth.social.core.connection.JpaUsersConnectRepository;
import com.honvay.cola.auth.social.core.repository.UserConnectionRepository;
import com.honvay.cola.auth.social.core.userdetails.SocialUserDetailsServiceImpl;
import com.honvay.cola.uc.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @author LIQIU
 */
@Configuration
@EnableSocial
@EnableConfigurationProperties(SocialProperties.class)
public class SocialAutoConfiguration extends SocialConfigurerAdapter {

	@Autowired
	private UserConnectionRepository userConnectionRepository;

	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;

	@Bean
	public SocialUserDetailsService socialUserDetailsService(UserService userService) {
		return new SocialUserDetailsServiceImpl(userService);
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JpaUsersConnectRepository repository = new JpaUsersConnectRepository(userConnectionRepository,
				connectionFactoryLocator, Encryptors.noOpText());
		if (connectionSignUp != null) {
			repository.setConnectionSignUp(connectionSignUp);
		}
		return repository;
	}
}
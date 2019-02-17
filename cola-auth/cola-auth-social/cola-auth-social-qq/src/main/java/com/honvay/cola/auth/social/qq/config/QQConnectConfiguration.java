package com.honvay.cola.auth.social.qq.config;

import com.honvay.cola.auth.social.qq.QQProperties;
import com.honvay.cola.auth.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.autoconfigure.SocialAutoConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * @author LIQIU
 */
@Configuration
@EnableConfigurationProperties(QQProperties.class)
public class QQConnectConfiguration extends SocialAutoConfigurerAdapter {

	@Autowired
	private QQProperties qqProperties;

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		return new QQConnectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(), qqProperties.getAppSecret());
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return null;
	}
}
package com.honvay.cola.auth.social.wechat.config;

import com.honvay.cola.auth.social.wechat.WechatProperties;
import com.honvay.cola.auth.social.wechat.connect.WechatConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.autoconfigure.SocialAutoConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * Created on 2018/1/11.
 *
 * @author LIQIU
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(WechatProperties.class)
public class WechatConnectionConfiguration extends SocialAutoConfigurerAdapter {

	@Autowired
	private WechatProperties wechatProperties;

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		WechatConnectionFactory connectionFactory = new WechatConnectionFactory(wechatProperties.getProviderId(), wechatProperties.getAppId(), wechatProperties.getAppSecret());
		connectionFactory.setScope(wechatProperties.getScope());
		return connectionFactory;
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return null;
	}
}

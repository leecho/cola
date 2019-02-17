package com.honvay.cola.auth.social.wechatmp.config;

import com.honvay.cola.auth.social.wechatmp.WechatMpProperties;
import com.honvay.cola.auth.social.wechatmp.connect.WechatMpConnectionFactory;
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
@EnableConfigurationProperties(WechatMpProperties.class)
public class WechatMpConnectionConfiguration extends SocialAutoConfigurerAdapter {

	@Autowired
	private WechatMpProperties wechatMpProperties;


	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		WechatMpConnectionFactory factory = new WechatMpConnectionFactory(wechatMpProperties.getProviderId(), wechatMpProperties.getAppId(),
				wechatMpProperties.getAppSecret());
		factory.setScope(wechatMpProperties.getScope());
		return factory;
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return null;
	}
}

package com.honvay.cola.auth.social.alipay.config;

import com.honvay.cola.auth.social.alipay.AlipayProperties;
import com.honvay.cola.auth.social.alipay.connect.AlipayConnectionFactory;
import com.honvay.cola.auth.social.alipay.connect.AlipayOAuth2AuthenticationService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.security.SocialAuthenticationServiceRegistry;

/**
 * @author LIQIU
 */
@Configuration
@EnableConfigurationProperties(AlipayProperties.class)
public class AlipayConnectConfiguration implements BeanPostProcessor {

	@Autowired
	private AlipayProperties properties;

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean.getClass().equals(SocialAuthenticationServiceRegistry.class)) {
			SocialAuthenticationServiceRegistry registry = (SocialAuthenticationServiceRegistry) bean;
			AlipayOAuth2AuthenticationService alipayOAuth2AuthenticationService = new AlipayOAuth2AuthenticationService((OAuth2ConnectionFactory) createConnectionFactory());
			registry.addAuthenticationService(alipayOAuth2AuthenticationService);
		}
		return bean;
	}

	protected ConnectionFactory<?> createConnectionFactory() {
		ConnectionFactory<?> factory = new AlipayConnectionFactory(properties);
		((AlipayConnectionFactory) factory).setScope(properties.getScope());
		return factory;
	}
}
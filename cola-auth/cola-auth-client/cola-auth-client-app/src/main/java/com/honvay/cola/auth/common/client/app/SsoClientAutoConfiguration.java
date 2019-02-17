package com.honvay.cola.auth.common.client.app;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LIQIU
 * created on 2018-11-26
 **/
@Configuration
@EnableConfigurationProperties(SsoClientProperties.class)
public class SsoClientAutoConfiguration {

	@Bean
	public SsoAuthClient ssoAuthClient(SsoClientProperties properties) {
		return new SsoAuthClient(properties);
	}

}

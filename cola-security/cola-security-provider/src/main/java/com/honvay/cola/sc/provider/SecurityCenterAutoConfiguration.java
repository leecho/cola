package com.honvay.cola.sc.provider;

import com.honvay.cola.sc.api.PasswordStrategy;
import com.honvay.cola.sc.provider.credential.CredentialProperties;
import com.honvay.cola.sc.provider.password.DefaultPasswordStrategy;
import com.honvay.cola.sc.provider.password.PasswordProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author LIQIU
 * created on 2018/12/26
 **/
@Configuration
@EnableConfigurationProperties({CredentialProperties.class, PasswordProperties.class})
public class SecurityCenterAutoConfiguration {

	@ConditionalOnBean(PasswordStrategy.class)
	public PasswordStrategy  passwordStrategy(PasswordProperties properties){
		return new DefaultPasswordStrategy(properties);
	}

}

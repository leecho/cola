package com.honvay.cola.auth.oauth2.sms.config;

import com.honvay.cola.auth.oauth2.sms.granter.SmsTokenGranter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * @author LIQIU
 * created on 2018/12/29
 **/
@Configuration
public class SmsOAuth2Configuration {


	@Bean
	@ConditionalOnBean({AuthenticationManager.class, AuthorizationServerTokenServices.class, ClientDetailsService.class})
	public SmsTokenGranter smsTokenGranter(AuthenticationManager authenticationManager,
										   AuthorizationServerTokenServices tokenServices,
										   ClientDetailsService clientDetailsService) {
		return new SmsTokenGranter(authenticationManager, tokenServices, clientDetailsService, new DefaultOAuth2RequestFactory(clientDetailsService));
	}


}

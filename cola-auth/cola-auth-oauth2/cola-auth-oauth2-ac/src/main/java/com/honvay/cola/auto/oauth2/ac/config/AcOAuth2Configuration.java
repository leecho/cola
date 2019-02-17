package com.honvay.cola.auto.oauth2.ac.config;

import com.honvay.cola.auto.oauth2.ac.granter.AcTokenGranter;
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
public class AcOAuth2Configuration {

	@Bean
	@ConditionalOnBean({AuthenticationManager.class, AuthorizationServerTokenServices.class, ClientDetailsService.class})
	public AcTokenGranter acTokenGranter(AuthenticationManager authenticationManager,
										 AuthorizationServerTokenServices tokenServices,
										 ClientDetailsService clientDetailsService) {
		return new AcTokenGranter(authenticationManager, tokenServices, clientDetailsService, new DefaultOAuth2RequestFactory(clientDetailsService));
	}

}

package com.honvay.cola.auth.oauth2.openid.config;

import com.honvay.cola.auth.oauth2.openid.granter.OpenIdTokenGranter;
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
public class OpenIdOAuth2Configuration {

	@Bean
	@ConditionalOnBean({AuthenticationManager.class, AuthorizationServerTokenServices.class, ClientDetailsService.class})
	public OpenIdTokenGranter openIdTokenGranter(AuthenticationManager authenticationManager,
												 AuthorizationServerTokenServices tokenServices,
												 ClientDetailsService clientDetailsService) {
		return new OpenIdTokenGranter(authenticationManager, tokenServices, clientDetailsService, new DefaultOAuth2RequestFactory(clientDetailsService));
	}

}

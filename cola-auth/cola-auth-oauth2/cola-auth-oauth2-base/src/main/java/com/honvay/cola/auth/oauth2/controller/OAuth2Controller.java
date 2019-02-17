package com.honvay.cola.auth.oauth2.controller;

import com.honvay.cola.framework.core.protocol.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author LIQIU
 * created on 2018-11-9
 **/
@RestController
public class OAuth2Controller {

	@Autowired
	private ConsumerTokenServices tokenServices;

	@RequestMapping("/api/userinfo")
	public Principal getCurrentPrincipal(Principal principal) {
		OAuth2Authentication auth2Authentication = (OAuth2Authentication) principal;
		OAuth2Authentication resultAuthentication;

		Authentication userAuthentication = auth2Authentication.getUserAuthentication();
		//重写认证主体
		if (!userAuthentication.getClass().equals(UsernamePasswordAuthenticationToken.class)) {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userAuthentication.getPrincipal(), userAuthentication.getCredentials(), userAuthentication.getAuthorities());
			resultAuthentication = new OAuth2Authentication(auth2Authentication.getOAuth2Request(), token);
		} else {
			resultAuthentication = auth2Authentication;
		}
		return resultAuthentication;
	}

	@RequestMapping("/oauth/revoke")
	public Result<String> revokeToken(@AuthenticationPrincipal OAuth2Authentication auth2Authentication) {
		tokenServices.revokeToken(auth2Authentication.getUserAuthentication().getName());
		return Result.success();
	}

}

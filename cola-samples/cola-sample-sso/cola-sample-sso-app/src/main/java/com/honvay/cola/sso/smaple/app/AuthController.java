package com.honvay.cola.sso.smaple.app;

import com.honvay.cola.auth.common.client.app.SsoAuthClient;
import com.honvay.cola.framework.core.protocol.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author LIQIU
 * created on 2018-11-25
 **/
@RestController
public class AuthController {

	@Autowired
	private SsoAuthClient ssoAuthClient;


	@GetMapping("/user")
	public Principal principal(Principal principal) {
		return principal;
	}

	@PostMapping("/login")
	public OAuth2AccessToken login(String username, String password) {
		return ssoAuthClient.login(username, password);
	}

	@GetMapping("/logout")
	public Result<String> logout() {
		return ssoAuthClient.logout();
	}

	@PostMapping("/loginByOpenId")
	public OAuth2AccessToken loginByQQ(String openId, String provider) {
		return ssoAuthClient.loginByOpenId(openId, provider);
	}

	@PostMapping("/loginBySms")
	public OAuth2AccessToken loginBySms(String phoneNumber, String verificationCode) {
		return ssoAuthClient.loginBySms(phoneNumber, verificationCode);
	}
}

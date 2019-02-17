package com.honvay.cola.auth.base.listener;

import com.honvay.cola.uc.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * 认证失败逻辑处理，页面登录和Token认证都会走这个逻辑
 *
 * @author LIQIU
 * created on 2018-11-19
 **/
@Component
@Slf4j
public class AuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Autowired
	private UserService userService;

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
		//只有账号密码登录才回更新登录失败次数
		if (authenticationFailureBadCredentialsEvent.getAuthentication().getClass().equals(UsernamePasswordAuthenticationToken.class)) {
			userService.processLoginFail(authenticationFailureBadCredentialsEvent.getAuthentication().getName());
			log.info("Authentication failure: " + authenticationFailureBadCredentialsEvent.getAuthentication().getName());
		}
	}
}

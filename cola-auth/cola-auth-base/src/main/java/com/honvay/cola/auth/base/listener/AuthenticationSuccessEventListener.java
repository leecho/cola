package com.honvay.cola.auth.base.listener;

import com.honvay.cola.uc.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author LIQIU
 * created on 2018-11-19
 **/
@Component
@Slf4j
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private UserService userService;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		if (event.getClass().equals(AuthenticationSuccessEvent.class)) {
			Authentication authentication = event.getAuthentication();
			this.userService.processLoginSuccess(authentication.getName(), null, null);
			log.info("Authentication success:" + authentication.getName() + " ," + AuthenticationSuccessEvent.class);
		}
	}
}

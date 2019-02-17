package com.honvay.cola.auth.jwt.listener;

import com.honvay.cola.auth.jwt.JwtTokenStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * @author LIQIU
 * created on 2018-11-19
 **/
@Slf4j
@Component
public class JwtAuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private JwtTokenStore jwtTokenStore;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		jwtTokenStore.save(event.getAuthentication().getName(), event.getAuthentication());
		if (log.isDebugEnabled()) {
			log.debug("Jwt token: [{}] store success", event.getAuthentication().getName());
		}
	}
}

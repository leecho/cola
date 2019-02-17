package com.honvay.cola.auth.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

/**
 * @author LIQIU
 * created on 2018-11-24
 **/
@Controller
@RequestMapping("/session")
public class SessionController {

	@Autowired
	private SessionRegistry sessionRegistry;

	@PostMapping("/revoke")
	public ResponseEntity<String> revoke(Principal principal) {
		sessionRegistry.getAllPrincipals();
		List<SessionInformation> sessionInformations = sessionRegistry
				.getAllSessions(principal, false);
		for (SessionInformation sessionInformation : sessionInformations) {
			sessionInformation.expireNow();

			sessionRegistry.removeSessionInformation(sessionInformation
					.getSessionId());

		}
		return ResponseEntity.ok().build();
	}

}

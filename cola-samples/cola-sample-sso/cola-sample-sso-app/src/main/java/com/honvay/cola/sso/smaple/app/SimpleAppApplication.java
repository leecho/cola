package com.honvay.cola.sso.smaple.app;

/**
 * @author LIQIU
 * created on 2018-11-26
 **/

import com.honvay.cola.auth.core.model.AuthenticatedUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LIQIU
 * created on 2018-11-26
 **/
@ComponentScan("com.honvay")
@RestController
@SpringBootApplication
public class SimpleAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleAppApplication.class, args);
	}

	@RequestMapping("/user")
	public AuthenticatedUser home(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		return authenticatedUser;
	}

}

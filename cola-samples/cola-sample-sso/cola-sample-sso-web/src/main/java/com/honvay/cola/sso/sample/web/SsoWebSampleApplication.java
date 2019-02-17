package com.honvay.cola.sso.sample.web;

import com.honvay.cola.auth.core.model.AuthenticatedUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author LIQIU
 * created on 2018-11-26
 **/
@RestController
@SpringBootApplication
@ComponentScan("com.honvay")
public class SsoWebSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoWebSampleApplication.class, args);
	}

	@RequestMapping("/")
	public ModelAndView home(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, Map<String, Object> map) {
		map.put("user", authenticatedUser.getUsername());
		return new ModelAndView("index", map);
	}

}

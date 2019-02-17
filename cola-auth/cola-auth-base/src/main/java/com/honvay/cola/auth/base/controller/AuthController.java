package com.honvay.cola.auth.base.controller;

import com.honvay.cola.auth.core.model.AuthenticatedUser;
import com.honvay.cola.framework.core.protocol.Result;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
@RestController
public class AuthController {

	@RequestMapping("/user/current")
	public Result<AuthenticatedUser> current(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		return Result.success(authenticatedUser);
	}
}

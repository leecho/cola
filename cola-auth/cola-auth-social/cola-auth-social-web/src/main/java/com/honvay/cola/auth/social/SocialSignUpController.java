package com.honvay.cola.auth.social;

import com.honvay.cola.framework.core.protocol.Result;
import com.honvay.cola.uc.api.UserService;
import com.honvay.cola.uc.api.enums.UserStatus;
import com.honvay.cola.uc.api.model.UserAddDto;
import com.honvay.cola.uc.api.model.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author LIQIU
 * created on 2018-11-25
 **/
@Controller
public class SocialSignUpController {

	private final String SIGNUP_FAIL_MESSAGE_KEY = "signup_error_message";

	private final String SIGNUP_TYPE_LOGIN = "login";

	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@GetMapping(value = "${spring.social.signupUrl:/social/signup}")
	public ModelAndView signUpPage(HttpServletRequest request, Map<String, Object> map) {
		SocialUserInfo userInfo = new SocialUserInfo();
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		if (connection != null) {
			userInfo.setProviderId(connection.getKey().getProviderId());
			userInfo.setProviderUserId(connection.getKey().getProviderUserId());
			userInfo.setNickname(connection.getDisplayName());
			userInfo.setHeadImg(connection.getImageUrl());
			map.put("user", userInfo);
			return new ModelAndView("/social/signup", map);
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	@ResponseBody
	@PostMapping(value = "/social/signup")
	public Result<String> signUp(String username, String password, String confirmPassword, String signupType, HttpServletRequest request) {
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		try {
			UserDto user;
			Assert.isTrue(StringUtils.isNotEmpty(signupType), "绑定类型不能为空");
			Assert.isTrue(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password), "账号和密码不能为空");
			if (SIGNUP_TYPE_LOGIN.equals(signupType)) {
				user = this.userService.findByUsername(username);
				Assert.notNull(user, "账号不存在");
				Assert.isTrue(passwordEncoder.matches(password, user.getPassword()), "账号或密码错误");
			} else {
				Assert.isTrue(password.equals(confirmPassword), "密码不匹配");
				UserAddDto userAddDto = UserAddDto.builder()
						.username(username)
						.password(password)
						.name("用户" + System.currentTimeMillis()).build();
				user = userService.add(userAddDto);
			}

			providerSignInUtils.doPostSignUp(user.getUsername(), new ServletWebRequest(request));
			return Result.success();

		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(e.getMessage());
		}
	}
}

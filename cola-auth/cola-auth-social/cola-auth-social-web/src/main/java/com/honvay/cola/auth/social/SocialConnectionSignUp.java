package com.honvay.cola.auth.social;

import com.honvay.cola.uc.api.UserService;
import com.honvay.cola.uc.api.enums.UserStatus;
import com.honvay.cola.uc.api.model.UserAddDto;
import com.honvay.cola.uc.api.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * 自动转换用户ID，可以自动创建用户
 *
 * @author LIQIU
 */

/**
 * @Component 由用户主动绑定账号
 */
public class SocialConnectionSignUp implements ConnectionSignUp {

	@Autowired
	private UserService userService;

	@Override
	public String execute(Connection<?> connection) {
		String username = "U" + System.currentTimeMillis();
		UserAddDto user = UserAddDto.builder().username(username)
				.password("123")
				.name("EU" + System.currentTimeMillis())
				.build();
		userService.add(user);
		//根据社交用户信息，默认创建用户并返回用户唯一标识
		return username;
	}
}
package com.honvay.cola.auth.web.sms.userdetails;

import com.honvay.cola.auth.core.model.AuthenticatedUser;
import com.honvay.cola.auth.web.sms.SmsUserDetailsService;
import com.honvay.cola.uc.api.UserService;
import com.honvay.cola.uc.api.enums.UserStatus;
import com.honvay.cola.uc.api.model.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author LIQIU
 * created on 2018/12/24
 **/
public class SmsUserDetailsServiceImpl implements SmsUserDetailsService {

	private UserService userService;

	public SmsUserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadByPhoneNumber(String phoneNumber) {

		UserDto userDto = userService.findByPhoneNumber(phoneNumber);
		if (userDto == null) {
			throw new UsernameNotFoundException("User " + phoneNumber + " can not be found");
		}

		return AuthenticatedUser.builder()
				.id(userDto.getId())
				.username(userDto.getUsername())
				.password(userDto.getPassword())
				.phoneNumber(userDto.getPhoneNumber())
				.email(userDto.getEmail())
				.avatar(userDto.getAvatar())
				.locked(UserStatus.LOCKED.getValue().equals(userDto.getStatus()))
				.enable(UserStatus.ACTIVE.getValue().equals(userDto.getStatus()))
				.build();

	}

}

package com.honvay.cola.auth.base.component;

import com.honvay.cola.auth.core.model.AuthenticatedUser;
import com.honvay.cola.uc.api.UserService;
import com.honvay.cola.uc.api.enums.UserStatus;
import com.honvay.cola.uc.api.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author LIQIU
 * @date 21.5.18
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDto user = userService.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " can not be found");
		}

		return buildAuthenticatedUser(user);
	}

	private AuthenticatedUser buildAuthenticatedUser(UserDto user) {
		return AuthenticatedUser.builder()
				.id(user.getId())
				.username(user.getUsername())
				.password(user.getPassword())
				.phoneNumber(user.getPhoneNumber())
				.email(user.getEmail())
				.avatar(user.getAvatar())
				.locked(UserStatus.LOCKED.getValue().equals(user.getStatus()))
				.enable(UserStatus.ACTIVE.getValue().equals(user.getStatus()))
				.build();
	}
}

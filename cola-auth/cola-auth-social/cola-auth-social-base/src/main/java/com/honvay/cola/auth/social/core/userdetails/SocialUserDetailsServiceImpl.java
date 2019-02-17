package com.honvay.cola.auth.social.core.userdetails;

import com.honvay.cola.auth.social.core.SocialAuthenticatedUser;
import com.honvay.cola.uc.api.UserService;
import com.honvay.cola.uc.api.model.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @author LIQIU
 * created on 2018/12/24
 **/
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

	private UserService userService;

	public SocialUserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		UserDto user = userService.findByUsername(userId);
		if (user == null) {
			throw new UsernameNotFoundException("User " + userId + " can not be found");
		}

		return buildSsoUser(user);

	}

	private SocialAuthenticatedUser buildSsoUser(UserDto user) {
		SocialAuthenticatedUser socialSsoUser = new SocialAuthenticatedUser();
		socialSsoUser.setId(user.getId());
		socialSsoUser.setUsername(user.getUsername());
		socialSsoUser.setPassword(user.getPassword());
		socialSsoUser.setEmail(user.getEmail());
		socialSsoUser.setPhoneNumber(user.getPhoneNumber());
		socialSsoUser.setAvatar(user.getAvatar());
		return socialSsoUser;
	}

}

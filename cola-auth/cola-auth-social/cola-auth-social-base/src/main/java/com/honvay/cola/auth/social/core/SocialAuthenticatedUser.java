package com.honvay.cola.auth.social.core;

import com.honvay.cola.auth.core.model.AuthenticatedUser;
import org.springframework.social.security.SocialUserDetails;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
public class SocialAuthenticatedUser extends AuthenticatedUser implements SocialUserDetails {
	@Override
	public String getUserId() {
		return this.getUsername();
	}
}

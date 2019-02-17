package com.honvay.cola.auth.web.sms.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 短信登录验证信息封装类
 *
 * @author TiHom
 */
public class SmsAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;

	private String credential;

	private String token;

	public SmsAuthenticationToken(Object principal, String credential, String token) {
		super(null);
		this.principal = principal;
		this.credential = credential;
		this.token = token;
		this.setAuthenticated(false);
	}

	public SmsAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		super.setAuthenticated(true);
	}


	@Override
	public Object getCredentials() {
		return credential;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		} else {
			super.setAuthenticated(false);
		}
	}

	@Override
	public void eraseCredentials() {
		this.credential = null;
		this.token = null;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

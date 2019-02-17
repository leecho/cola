package com.honvay.cola.auth.openid;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 短信登录验证信息封装类
 *
 * @author TiHom
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;

	private String provider;

	public OpenIdAuthenticationToken(Object principal, String provider) {
		super(null);
		this.principal = principal;
		this.provider = provider;
		this.setAuthenticated(false);
	}

	public OpenIdAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		super.setAuthenticated(true);
	}


	@Override
	public Object getCredentials() {
		return provider;
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
		this.provider = null;
	}

}

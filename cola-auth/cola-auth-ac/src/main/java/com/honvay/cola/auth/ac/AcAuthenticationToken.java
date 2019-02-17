package com.honvay.cola.auth.ac;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 短信登录验证信息封装类
 *
 * @author TiHom
 */
public class AcAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;

	private String authorizationCode;

	private String provider;

	public AcAuthenticationToken(String authorizationCode, String provider) {
		super(null);
		this.principal = null;
		this.authorizationCode = authorizationCode;
		this.provider = provider;
		this.setAuthenticated(false);
	}

	public AcAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		super.setAuthenticated(true);
	}


	@Override
	public Object getCredentials() {
		return authorizationCode;
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
		this.authorizationCode = null;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}

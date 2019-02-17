package com.honvay.cola.auth.client.common;

import com.honvay.cola.auth.core.model.AuthenticatedUser;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedPrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author LIQIU
 * created on 2018-11-26
 **/
@Component
public class SsoUserExtractor implements PrincipalExtractor {

	private FixedPrincipalExtractor fixedPrincipalExtractor = new FixedPrincipalExtractor();

	@Override
	public Object extractPrincipal(Map<String, Object> map) {
		Object authentication = map.get("userAuthentication");
		if (authentication == null) {
			throw new InvalidTokenException("userAuthentication is empty");
		}
		Object principal = ((Map<String, Object>) authentication).get("principal");
		AuthenticatedUser user = new AuthenticatedUser();
		if (principal == null) {
			throw new InvalidTokenException("principal is empty");
		}
		try {
			BeanUtils.populate(user, (Map<String, Object>) principal);
		} catch (Exception e) {
			throw new InvalidTokenException("populate user error: " + e.getMessage());
		}
		return user;
	}
}

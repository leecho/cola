package com.honvay.cola.auth.openid.provider;

import com.honvay.cola.auth.openid.OpenIdAuthenticationToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * @author LIQIU
 * created on 2018-11-19
 **/
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

	protected final Log logger = LogFactory.getLog(getClass());

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private SocialUserDetailsService userDetailsService;

	private UsersConnectionRepository usersConnectionRepository;

	private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();

	private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();

	public OpenIdAuthenticationProvider(SocialUserDetailsService userDetailsService, UsersConnectionRepository usersConnectionRepository) {
		this.userDetailsService = userDetailsService;
		this.usersConnectionRepository = usersConnectionRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(OpenIdAuthenticationToken.class, authentication, "unsupported authentication type");
		Assert.isTrue(!authentication.isAuthenticated(), "already authenticated");
		OpenIdAuthenticationToken authToken = (OpenIdAuthenticationToken) authentication;

		String userId = toUserId(authToken);
		if (userId == null) {
			throw new BadCredentialsException("Unknown access token");
		}

		UserDetails userDetails = userDetailsService.loadUserByUserId(userId);
		if (userDetails == null) {
			throw new UsernameNotFoundException("Unknown connected account id");
		}
		preAuthenticationChecks.check(userDetails);
		postAuthenticationChecks.check(userDetails);
		return this.createSuccessAuthentication(userDetails, authentication, userDetails);
	}


	protected String toUserId(OpenIdAuthenticationToken token) {
		Set<String> providerUserId = new HashSet<>();
		providerUserId.add(String.valueOf(token.getPrincipal()));
		Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(String.valueOf(token.getCredentials()),providerUserId);
		// only if a single userId is connected to this providerUserId
		return (userIds.size() == 1) ? userIds.iterator().next() : null;
	}


	protected Authentication createSuccessAuthentication(Object principal,
														 Authentication authentication, UserDetails user) {
		OpenIdAuthenticationToken result = new OpenIdAuthenticationToken(
				principal,
				user.getAuthorities());
		result.setDetails(authentication.getDetails());

		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(OpenIdAuthenticationToken.class);
	}

	private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
		@Override
		public void check(UserDetails user) {
			if (!user.isAccountNonLocked()) {
				logger.debug("User account is locked");

				throw new LockedException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.locked",
						"User account is locked"));
			}

			if (!user.isEnabled()) {
				logger.debug("User account is disabled");

				throw new DisabledException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.disabled",
						"User is disabled"));
			}

			if (!user.isAccountNonExpired()) {
				logger.debug("User account is expired");

				throw new AccountExpiredException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.expired",
						"User account has expired"));
			}
		}
	}

	private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
		@Override
		public void check(UserDetails user) {
			if (!user.isCredentialsNonExpired()) {
				logger.debug("User account credentials have expired");

				throw new CredentialsExpiredException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.credentialsExpired",
						"User credentials have expired"));
			}
		}
	}
}

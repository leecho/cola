package com.honvay.cola.auth.ac.provider;

import com.honvay.cola.auth.ac.AcAuthenticationToken;
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
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author LIQIU
 * created on 2018-11-19
 **/
public class AcAuthenticationProvider implements AuthenticationProvider {

	protected final Log logger = LogFactory.getLog(getClass());

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private SocialUserDetailsService userDetailsService;

	private ConnectionFactoryLocator connectionFactoryLocator;

	private UsersConnectionRepository usersConnectionRepository;

	private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();

	private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();

	public AcAuthenticationProvider(SocialUserDetailsService userDetailsService,
									ConnectionFactoryLocator connectionFactoryLocator,
									UsersConnectionRepository usersConnectionRepository) {
		this.userDetailsService = userDetailsService;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.usersConnectionRepository = usersConnectionRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(AcAuthenticationToken.class, authentication, "unsupported authentication type");
		Assert.isTrue(!authentication.isAuthenticated(), "already authenticated");
		AcAuthenticationToken authToken = (AcAuthenticationToken) authentication;
		OAuth2ConnectionFactory<?> connectionFactory;
		try {
			connectionFactory = (OAuth2ConnectionFactory) connectionFactoryLocator.getConnectionFactory(authToken.getProvider());
		}catch (Exception e){
			throw new InsufficientAuthenticationException("不支持的第三方平台");
		}
		AccessGrant accessGrant;
		try {
			accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(authToken.getAuthorizationCode(), null, null);
		} catch (Exception e) {
			throw new InsufficientAuthenticationException("获取AccessToken失败");
		}
		// TODO avoid API call if possible (auth using token would be fine)
		Connection<?> connection = connectionFactory.createConnection(accessGrant);

		String userId = toUserId(connection);
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


	protected String toUserId(Connection<?> connection) {
		List<String> userIds = usersConnectionRepository.findUserIdsWithConnection(connection);
		return (userIds.size() == 1) ? userIds.get(0) : null;
	}


	protected Authentication createSuccessAuthentication(Object principal,
														 Authentication authentication, UserDetails user) {
		AcAuthenticationToken result = new AcAuthenticationToken(
				principal,
				user.getAuthorities());
		result.setDetails(authentication.getDetails());

		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(AcAuthenticationToken.class);
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

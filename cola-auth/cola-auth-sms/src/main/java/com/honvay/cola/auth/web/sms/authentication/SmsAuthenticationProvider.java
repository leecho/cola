package com.honvay.cola.auth.web.sms.authentication;

import com.honvay.cola.auth.web.sms.SmsUserDetailsService;
import com.honvay.cola.sc.api.CredentialService;
import com.honvay.cola.sc.api.model.CredentialValidation;
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
import org.springframework.util.Assert;

/**
 * @author LIQIU
 * created on 2018-11-19
 **/
public class SmsAuthenticationProvider implements AuthenticationProvider {

	protected final Log logger = LogFactory.getLog(getClass());

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private SmsUserDetailsService smsUserDetailsService;

	private CredentialService credentialService;

	private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();

	private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();

	public SmsAuthenticationProvider(SmsUserDetailsService smsUserDetailsService, CredentialService credentialService) {
		this.smsUserDetailsService = smsUserDetailsService;
		this.credentialService = credentialService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;

		CredentialValidation validate = new CredentialValidation();
		validate.setPrincipal((String) smsAuthenticationToken.getPrincipal());
		validate.setToken(smsAuthenticationToken.getToken());
		validate.setCredential((String) smsAuthenticationToken.getCredentials());
		validate.setIgnoreCase(false);

		//判断是否匹配
		if (!credentialService.validate(validate)) {
			throw new BadCredentialsException("The Credential is not match");
		}

		UserDetails user;
		try {
			//通过手机号获取用户
			user = smsUserDetailsService.loadByPhoneNumber(String.valueOf(authentication.getPrincipal()));
		} catch (UsernameNotFoundException notFound) {
			logger.debug("User '" + authentication.getName() + "' not found");
			throw notFound;
		}

		Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
		preAuthenticationChecks.check(user);
		postAuthenticationChecks.check(user);
		return this.createSuccessAuthentication(user, authentication, user);
	}

	protected Authentication createSuccessAuthentication(Object principal,
														 Authentication authentication, UserDetails user) {
		SmsAuthenticationToken result = new SmsAuthenticationToken(
				principal,
				user.getAuthorities());
		result.setDetails(authentication.getDetails());

		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(SmsAuthenticationToken.class);
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

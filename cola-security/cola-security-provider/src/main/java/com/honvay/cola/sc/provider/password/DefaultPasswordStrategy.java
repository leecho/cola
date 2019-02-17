package com.honvay.cola.sc.provider.password;

import com.honvay.cola.sc.api.PasswordStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * @author LIQIU
 * created on 2018/12/29
 **/
public class DefaultPasswordStrategy implements PasswordStrategy {

	private final PasswordProperties properties;

	protected MessageSourceAccessor messages;


	public DefaultPasswordStrategy(PasswordProperties properties) {
		this.properties = properties;
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messages = new MessageSourceAccessor(messageSource);
	}

	/**
	 * 检查密码强度
	 *
	 * @param password 密码
	 */
	@Override
	public void check(String password) {
		if (StringUtils.isEmpty(password)) {
			throw new InvalidPasswordException(messages.getMessage("PasswordIsNull", "Password must not be null"));
		}

		if (StringUtils.length(password) < properties.getMinLength()) {
			throw new InvalidPasswordException(messages.getMessage("PasswordIsTooShort", "The length of password is too short"));
		}

		if (StringUtils.length(password) > properties.getMinLength()) {
			throw new InvalidPasswordException(messages.getMessage("PasswordIsTooLong", "The length of password is too long"));
		}
	}

}

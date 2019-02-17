package com.honvay.cola.sc.provider.password;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIQIU
 * created on 2018/12/29
 **/
@Data
@ConfigurationProperties(prefix = "cola.security.password")
public class PasswordProperties {

	/**
	 * 最长长度
	 */
	private Integer maxLength = 100;

	/**
	 * 最小长度
	 */
	private Integer minLength = 6;

	/**
	 * 强度
	 */
	private PasswordStrength strength = PasswordStrength.NORMAL;
}

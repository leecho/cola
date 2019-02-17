package com.honvay.cola.sc.provider.password;

/**
 * @author LIQIU
 * created on 2018/12/29
 **/
public enum PasswordStrength {

	/**
	 * 任意密码
	 */
	WEAK,
	/**
	 * 数字加字母
	 */
	LOW,
	/**
	 * 数字加大小写字母
	 */
	NORMAL,
	/**
	 * 数字加大小写字母加特殊字符
	 */
	HIGH;

}

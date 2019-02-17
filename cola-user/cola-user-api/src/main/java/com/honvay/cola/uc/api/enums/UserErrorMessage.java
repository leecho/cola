package com.honvay.cola.uc.api.enums;


import com.honvay.cola.framework.core.ErrorMessage;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
public enum UserErrorMessage implements ErrorMessage {

	/**
	 * 用户不能存在
	 */
	USER_NOT_EXISTS("UC000001", "用户不存在"),
	/**
	 * 邮箱已存在
	 */
	USER_EMAIL_EXISTS("UC000002", "邮箱已注册"),

	/**
	 * 用户名已注册
	 */
	USER_NAME_EXISTS("UC000003", "用户名已注册"),

	/**
	 * 手机号已存在
	 */
	USER_PHONE_NUMBER_EXISTS("UC000004", "手机号已存在"),
	/**
	 * 用户状态错误
	 */
	USER_STATUS_ILLEGAL("UC000005", "用户状态错误"),
	/**
	 * 原始密码错误
	 */
	ORIGIN_PASSWORD_NOT_MATCHED("UC000006", "原始密码错误"),

	/**
	 * 确认密码不匹配
	 */
	CONFIRM_PASSWORD_NOT_MATCHED("UC000007", "确认密码不匹配"),

	PHONE_NUMBER_ILLEGAL("UC000008", "手机号码格式错误"),

	SMS_CREDENTIAL_NOT_MATCHED("UC000009", "短信验证码错误"),

	/**
	 * 无效密码
	 */
	PASSWORD_INVALID("UC000010", "无效密码");

	private String code;
	private String message;

	UserErrorMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
}

package com.honvay.cola.sc.api.enums;

import com.honvay.cola.framework.core.ErrorMessage;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
public enum SecurityErrorEnum implements ErrorMessage {
	;
	private String code;
	private String message;

	SecurityErrorEnum(String code, String message) {
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

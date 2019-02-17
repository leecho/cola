package com.honvay.cola.sc.provider.password;

/**
 * @author LIQIU
 * created on 2019/1/24
 **/
public class InvalidPasswordException extends RuntimeException {

	public InvalidPasswordException(String message) {
		super(message);
	}
}

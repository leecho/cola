package com.honvay.cola.sc.api;


/**
 * @author LIQIU
 * created on 2018/12/29
 **/
public interface PasswordStrategy {


	/**
	 * 检查密码强度
	 *
	 * @param password 密码
	 */
	void check(String password);

}

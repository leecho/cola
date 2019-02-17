package com.honvay.cola.auth.web.sms;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author LIQIU
 * created on 2018-11-21
 **/
public interface SmsUserDetailsService {

	/**
	 * 通过手机号获取用户信息
	 * @param phoneNumber 手机号码
	 * @return 用户信息
	 */
	UserDetails loadByPhoneNumber(String phoneNumber);

}

package com.honvay.cola.sc.api.model;

import com.honvay.cola.nc.api.model.Notification;
import lombok.Data;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
@Data
public class CredentialNotify {

	/**
	 * 凭证令牌
	 */
	private String token;

	/**
	 * 应用
	 */
	private String application;

	/**
	 * 主体
	 */
	private String principal;

	/**
	 * 通知
	 */
	private Notification notification;

}

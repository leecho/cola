package com.honvay.cola.uc.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author LIQIU
 * created on 2019/1/14
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddDto {

	/**
	 * 用户名
	 */
	@NotNull
	private String username;

	/**
	 * 密码
	 */
	@NotNull
	private String password;

	/**
	 * 名称
	 */
	@NotNull
	private String name;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号码
	 */
	private String phoneNumber;


}

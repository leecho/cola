package com.honvay.cola.uc.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author LIQIU
 * created on 2018/12/24
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	/**
	 * ID
	 */
	private Integer id;

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

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginDate;

	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;

	/**
	 * 最后登录位置
	 */
	private String lastLoginLocation;

	/**
	 * 登录失败次数
	 */
	private Integer loginFailTimes;

	/**
	 * 状态： 1、正常 2、锁定 3、失效
	 */
	private Integer status;

}

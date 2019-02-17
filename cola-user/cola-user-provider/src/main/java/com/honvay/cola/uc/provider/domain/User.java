package com.honvay.cola.uc.provider.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author LIQIU
 * created on 2018-11-16
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cola_user", uniqueConstraints = {
		@UniqueConstraint(name = "uni_cola_user_username", columnNames = {"username"}),
		@UniqueConstraint(name = "uni_cola_user_email", columnNames = {"email"}),
		@UniqueConstraint(name = "uni_cola_user_phoneNumber", columnNames = {"phoneNumber"})
})
public class User {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 用户名
	 */
	@NotNull
	@Column(length = 100)
	private String username;

	/**
	 * 密码
	 */
	@NotNull
	@Column(length = 100)
	private String password;

	/**
	 * 名称
	 */
	@NotNull
	@Column(length = 50)
	private String name;

	/**
	 * 邮箱
	 */
	@Email
	@Column(length = 100)
	private String email;

	/**
	 * 手机号码
	 */
	@Column(length = 20)
	private String phoneNumber;

	/**
	 * 头像
	 */
	@Column(length = 200)
	private String avatar;

	/**
	 * 性别
	 */
	@Column(length = 10)
	private String gender;

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
	@Column(length = 2)
	private Integer status;
}

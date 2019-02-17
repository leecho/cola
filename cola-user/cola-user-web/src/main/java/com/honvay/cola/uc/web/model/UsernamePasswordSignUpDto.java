package com.honvay.cola.uc.web.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author LIQIU
 * created on 2018/12/29
 **/
@Data
public class UsernamePasswordSignUpDto {

	@NotEmpty(message = "确认密码不能为空")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "用户名只允许为数字或英文字母")
	private String username;

	@NotEmpty
	@Size(max = 100, message = "名称最多包含50个字符")
	private String name;

	@NotEmpty(message = "密码不能为空")
	private String password;

	@NotEmpty(message = "确认密码不能为空")
	private String confirmPassword;

}

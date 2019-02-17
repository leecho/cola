package com.honvay.cola.uc.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
@Data
@ApiModel(description = "修改密码参数")
public class UpdatePasswordDto {

	@ApiModelProperty(name = "用户ID", required = true)
	private Integer id;

	@NotEmpty(message = "原密码不能为空")
	@ApiModelProperty(name = "原密码", required = true)
	private String oldPassword;

	@NotEmpty(message = "新密码不能为空")
	@Size(min = 6, max = 50, message = "密码长度最少6位，最多50位")
	@ApiModelProperty(name = "新密码", required = true)
	private String newPassword;

	@NotEmpty(message = "确认密码能为空")
	@ApiModelProperty(name = "确认新密码", required = true)
	private String confirmNewPassword;

}

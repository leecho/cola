package com.honvay.cola.uc.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
@Data
@ApiModel(description = "修改用户信息")
public class UserUpdateDto {

	@ApiModelProperty(name = "姓名",required = true)
	private String name;

	@ApiModelProperty(name = "性别",required = true)
	private String gender;
}

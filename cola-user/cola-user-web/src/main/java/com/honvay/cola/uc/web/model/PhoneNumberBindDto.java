package com.honvay.cola.uc.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author LIQIU
 * created on 2018/12/29
 **/
@Data
@ApiModel("短信注册参数")
public class PhoneNumberBindDto {

	/**
	 * 手机号码
	 */
	@NotEmpty
	@ApiModelProperty(value = "手机号码", required = true)
	private String phoneNumber;

	/**
	 * 短信令牌
	 */
	@NotEmpty
	@ApiModelProperty(value = "短信令牌", required = true)
	private String token;

	/**
	 * 短信凭证
	 */
	@NotEmpty
	@ApiModelProperty(value = "短信凭证", required = true)
	private String credential;

}

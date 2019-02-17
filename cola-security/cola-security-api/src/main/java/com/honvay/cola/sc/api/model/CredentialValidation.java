package com.honvay.cola.sc.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author LIQIU
 * @date 2018-7-10
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialValidation implements Serializable {

	/**
	 * 凭证主体
	 */
	@NotEmpty(message = "主体不能为空")
	private String principal;

	/**
	 * 应用
	 */
	@NotEmpty(message = "应用不能为空")
	private String application;

	/**
	 * 凭证令牌
	 */
	@NotEmpty(message = "令牌不能为空")
	private String token;

	/**
	 * 凭证
	 */
	@NotEmpty(message = "凭证不能为空")
	private String credential;

	/**
	 * 是否忽略大小写
	 */
	private Boolean ignoreCase;

}

package com.honvay.cola.sc.api.model;

import com.honvay.cola.nc.api.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author LIQIU
 * @date 2018-7-10
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialConfig implements Serializable {

	/**
	 * 凭证大小
	 */
	@Min(value = 4, message = "凭证长度最小长度不能小于4位")
	@Min(value = 50, message = "凭证长度最大长度不能大于50位")
	@NotNull(message = "凭证长度不能为空")
	private Integer size;

	/**
	 * 应用
	 */
	@NotEmpty(message = "主体不能为空")
	private String application;

	/**
	 * 凭证类型
	 */
	@NotNull(message = "凭证类型不能为空")
	private CredentialType type;

	/**
	 * 主体
	 */
	@NotEmpty(message = "主体不能为空")
	private String principal;

	/**
	 * 过期时间
	 */
	@Min(value = 1000 * 15, message = "凭证最少失效时间为15秒")
	@Max(value = 1000 * 60 * 60, message = "凭证最大时间薇1小时")
	private Long expire;

	/**
	 * 通知
	 */
	private Notification notification;

}

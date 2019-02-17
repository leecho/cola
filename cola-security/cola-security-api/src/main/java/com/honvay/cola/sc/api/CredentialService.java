package com.honvay.cola.sc.api;

import com.honvay.cola.sc.api.model.CredentialConfig;
import com.honvay.cola.sc.api.model.CredentialNotify;
import com.honvay.cola.sc.api.model.CredentialValidation;

import javax.validation.Valid;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
@Valid
public interface CredentialService {

	/**
	 * 生成凭证
	 *
	 * @param request 请求
	 * @return 凭证Token
	 */
	String generate(@Valid CredentialConfig request);

	/**
	 * 验证凭证
	 *
	 * @param request 请求
	 * @return 是否匹配
	 */
	boolean validate(@Valid CredentialValidation request);

	/**
	 * 将凭证通知到用户
	 *
	 * @param request 通知请求
	 */
	void notify(CredentialNotify request);

	/**
	 * 获取凭证内容
	 *
	 * @param application 应用
	 * @param principal 主体
	 * @param token     凭证令牌
	 * @return 凭证
	 */
	String getCredential(@Valid String application, String principal, String token);
}

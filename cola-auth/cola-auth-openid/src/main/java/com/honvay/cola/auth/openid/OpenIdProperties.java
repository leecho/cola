package com.honvay.cola.auth.openid;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIQIU
 * created on 2018/12/27
 **/
@Data
@ConfigurationProperties(prefix = "security.openid")
public class OpenIdProperties {

	private String loginSuccessUrl = "/";

	private String loginProcessUrl = "/loginByOpenId";

	private String loginFailureUrl = "/loginByOpenId?error";

}

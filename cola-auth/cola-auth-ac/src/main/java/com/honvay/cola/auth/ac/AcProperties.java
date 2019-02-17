package com.honvay.cola.auth.ac;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIQIU
 * created on 2018/12/27
 **/
@Data
@ConfigurationProperties(prefix = "security.ac")
public class AcProperties {

	private String loginSuccessUrl = "/";

	private String loginProcessUrl = "/loginByAuthorizationCode";

	private String loginFailureUrl = "/loginByAuthorizationCode?error";

}

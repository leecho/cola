package com.honvay.cola.auth.social.qq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIQIU
 * created on 2018-11-13
 **/
@Data
@ConfigurationProperties(prefix = "spring.social.qq")
public class QQProperties {

	private String appId;

	private String appSecret;

	private String providerId = "qq";

}

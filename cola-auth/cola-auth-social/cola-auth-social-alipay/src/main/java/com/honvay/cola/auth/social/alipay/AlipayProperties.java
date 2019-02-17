package com.honvay.cola.auth.social.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIQIU
 * created on 2018-11-26
 **/
@Data
@ConfigurationProperties(prefix = "spring.social.alipay")
public class AlipayProperties {

	private String appId;

	private String appSecret;

	private String providerId = "alipay";

	private String signType = "RSA2";

	private String scope = "auth_user";

	private String publicKey;

	private String privateKey;

	private String charset = "GBK";

	private String format = "json";

	private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}

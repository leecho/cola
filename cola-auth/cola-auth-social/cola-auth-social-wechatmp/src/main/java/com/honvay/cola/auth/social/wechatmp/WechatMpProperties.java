package com.honvay.cola.auth.social.wechatmp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIQIU
 * created on 2018-11-15
 **/
@Data
@ConfigurationProperties("spring.social.wechatmp")
public class WechatMpProperties {

	private String appId;

	private String appSecret;

	private String providerId = "wechatmp";

	private String scope = "snsapi_userinfo";

}

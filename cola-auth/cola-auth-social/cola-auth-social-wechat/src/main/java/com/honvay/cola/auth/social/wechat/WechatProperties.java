package com.honvay.cola.auth.social.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIQIU
 * created on 2018-11-15
 **/
@Data
@ConfigurationProperties("spring.social.wechat")
public class WechatProperties {

	private String appId;

	private String appSecret;

	private String providerId = "wechat";

	private String scope = "snsapi_login";

}

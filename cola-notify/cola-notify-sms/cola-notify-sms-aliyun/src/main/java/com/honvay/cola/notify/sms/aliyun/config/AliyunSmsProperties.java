package com.honvay.cola.notify.sms.aliyun.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIQIU
 */
@Data
@ConfigurationProperties(prefix = "spring.notify.sms.aliyun")
public class AliyunSmsProperties {

	private String accessKeyId;
	private String accessKeySecret;
}

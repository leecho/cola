package com.honvay.cola.notify.sms.aliyun.config;

import com.honvay.cola.notify.sms.aliyun.AliyunSmsSender;
import com.honvay.cola.notify.sms.api.sender.SmsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LIQIU
 * created on 2019/1/14
 **/
@Configuration
@EnableConfigurationProperties(AliyunSmsProperties.class)
public class AliyunSmsSenderConfiguration {

	@Autowired
	private AliyunSmsProperties aliyunSmsProperties;


	@Bean
	@ConditionalOnClass({AliyunSmsSender.class})
	@ConditionalOnProperty(name = "spring.notify.sms.aliyun.accessKeyId")
	public SmsSender smsSender() {
		AliyunSmsSender sender = new AliyunSmsSender();
		BeanUtils.copyProperties(aliyunSmsProperties, sender);
		return sender;
	}

}

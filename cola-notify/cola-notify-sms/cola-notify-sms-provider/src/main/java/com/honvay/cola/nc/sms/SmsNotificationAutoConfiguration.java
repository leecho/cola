package com.honvay.cola.nc.sms;

import com.honvay.cola.nc.sms.exchanger.SmsNotificationExchanger;
import com.honvay.cola.notify.sms.api.sender.SmsSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author LIQIU
 * @date 2018-3-27
 **/
@Configuration
public class SmsNotificationAutoConfiguration {

	@Bean
	public SmsNotificationExchanger smsNotificationExchanger(SmsSender smsSender,
															 StringRedisTemplate redisTemplate) {
		//每分钟发送短信位20次，写死
		return new SmsNotificationExchanger(smsSender, redisTemplate, 20L);
	}
}

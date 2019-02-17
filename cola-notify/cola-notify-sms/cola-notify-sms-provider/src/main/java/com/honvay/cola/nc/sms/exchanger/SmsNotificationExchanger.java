package com.honvay.cola.nc.sms.exchanger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.honvay.cola.nc.api.exchanger.NotificationExchanger;
import com.honvay.cola.notify.sms.api.SmsNotification;
import com.honvay.cola.notify.sms.api.sender.SmsParameter;
import com.honvay.cola.notify.sms.api.sender.SmsSendResult;
import com.honvay.cola.notify.sms.api.sender.SmsSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 短信通知转发器，可以配置<b>cola.notification.sms.sign-name</b>设置默认的短信签名
 *
 * @author LIQIU
 * @date 2018-3-27
 **/
@Slf4j
public class SmsNotificationExchanger implements NotificationExchanger<SmsNotification> {

	private static String SMS_NOTIFY_COUNTER_CACHE = "sms_counter";

	private SmsSender smsSender;

	private final static String STATUS_OK = "OK";

	private StringRedisTemplate redisTemplate;

	private Long limitOfOneMinutes;

	public SmsNotificationExchanger(SmsSender smsSender, StringRedisTemplate redisTemplate, Long limitOfOneMinutes) {
		if (smsSender != null) {
			log.info("初始化短信通知组件");
		}
		this.smsSender = smsSender;
		this.redisTemplate = redisTemplate;
		this.limitOfOneMinutes = limitOfOneMinutes;
	}

	@Value("cola.notification.sms.sign-name")
	private String signName;

	private String getCacheKey(String phoneNumber) {
		return SMS_NOTIFY_COUNTER_CACHE + ":" + phoneNumber;
	}

	@Override
	public boolean exchange(SmsNotification smsNotification) {

		Assert.notNull(smsSender, "短信接口没有初始化");

		String cacheKey = this.getCacheKey(smsNotification.getReceiver());

		Long currentTimes = this.redisTemplate.opsForValue().increment(cacheKey);
		if (currentTimes == null || currentTimes.compareTo(1L) == 0) {
			this.redisTemplate.expire(cacheKey, 1, TimeUnit.MILLISECONDS);
		} else if (currentTimes.compareTo(limitOfOneMinutes) > 0) {
			log.error("Sms notify was rejected, phoneNumber：{} is exceed limit");
			return false;
		}

		SmsParameter parameter = new SmsParameter();
		parameter.setPhoneNumbers(Collections.singletonList(smsNotification.getReceiver()));
		parameter.setTemplateCode(smsNotification.getTemplateCode());

		if (StringUtils.isEmpty(smsNotification.getSignName())) {
			smsNotification.setSignName(this.signName);
		}

		Assert.notNull(smsNotification.getSignName(), "短信签名不能为空");

		parameter.setSignName(smsNotification.getSignName());
		if (smsNotification.getParams() != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				parameter.setParams(mapper.writeValueAsString(smsNotification.getParams()));
			} catch (JsonProcessingException e) {
				throw new RuntimeException("格式化短信参数失败");
			}
		}

		SmsSendResult smsSendResult = smsSender.send(parameter);
		return STATUS_OK.equals(smsSendResult.getCode());
	}
}

package com.honvay.cola.notify.sms.api.sender;

/**
 * 短信发送器
 *
 * @author LIQIU
 */
public interface SmsSender {

	/**
	 * 发送短信
	 *
	 * @param parameter
	 * @return
	 */
	SmsSendResult send(SmsParameter parameter);
}

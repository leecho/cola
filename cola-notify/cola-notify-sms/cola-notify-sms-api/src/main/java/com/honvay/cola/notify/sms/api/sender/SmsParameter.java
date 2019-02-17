package com.honvay.cola.notify.sms.api.sender;

import lombok.Data;

import java.util.List;

/**
 * 短信
 *
 * @author LIQIU
 */
@Data
public class SmsParameter {

	private String templateCode;
	private String signName;
	private List<String> phoneNumbers;
	private String params;
}

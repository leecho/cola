package com.honvay.cola.auth.web.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @author LIQIU
 * created on 2018-11-22
 **/
@Data
@ConfigurationProperties(prefix = "security.sms")
public class SmsCredentialProperties {

	private String loginSuccessUrl = "/";

	private String loginProcessUrl = "/loginBySms";

	private String loginFailureUrl = "/loginBySms?error";

	private String sendSmsUrl = "/sendSms";

	private String smsTemlateCode;

	private String smsSignName;

	private Integer credentialLength = 6;

	private Duration timeout = Duration.ofMinutes(3);

}

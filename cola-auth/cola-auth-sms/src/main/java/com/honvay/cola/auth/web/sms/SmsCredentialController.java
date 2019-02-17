package com.honvay.cola.auth.web.sms;

import com.honvay.cola.auth.web.sms.authentication.SmsAuthenticationFilter;
import com.honvay.cola.notify.sms.api.SmsNotification;
import com.honvay.cola.sc.api.CredentialService;
import com.honvay.cola.sc.api.model.CredentialConfig;
import com.honvay.cola.sc.api.model.CredentialType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


/**
 * @author LIQIU
 * created on 2018-11-22
 **/
@RestController
public class SmsCredentialController {

	@Autowired
	private CredentialService credentialService;

	@Autowired
	private SmsCredentialProperties properties;

	@GetMapping("${security.sms.sendSmsUrl:/sendSms}")
	public ResponseEntity<?> sendSms(String phoneNumber, HttpSession session) {
		SmsNotification smsNotification = new SmsNotification();
		smsNotification.setReceiver(phoneNumber);
		smsNotification.setTemplateCode(properties.getSmsTemlateCode());
		smsNotification.setSignName(properties.getSmsSignName());

		CredentialConfig config = new CredentialConfig();
		config.setPrincipal(phoneNumber);
		config.setSize(properties.getCredentialLength());
		config.setType(CredentialType.NUMBER);
		config.setExpire(properties.getTimeout().toMillis());
		config.setNotification(smsNotification);
		String token = credentialService.generate(config);
		return ResponseEntity.ok(token);
	}

}

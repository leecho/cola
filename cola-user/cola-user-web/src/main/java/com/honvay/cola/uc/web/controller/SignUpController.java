package com.honvay.cola.uc.web.controller;

import com.honvay.cola.framework.core.ServiceAssert;
import com.honvay.cola.framework.core.exception.ServiceException;
import com.honvay.cola.framework.core.protocol.Result;
import com.honvay.cola.framework.util.PatternUtils;
import com.honvay.cola.framework.util.RandomUtils;
import com.honvay.cola.notify.sms.api.SmsNotification;
import com.honvay.cola.sc.api.CredentialService;
import com.honvay.cola.sc.api.model.CredentialConfig;
import com.honvay.cola.sc.api.model.CredentialType;
import com.honvay.cola.sc.api.model.CredentialValidation;
import com.honvay.cola.uc.api.UserService;
import com.honvay.cola.uc.api.enums.UserErrorMessage;
import com.honvay.cola.uc.api.model.UserAddDto;
import com.honvay.cola.uc.web.model.UsernamePasswordSignUpDto;
import com.honvay.cola.uc.web.model.SmsSignUpDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Duration;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
@Api(description = "注册服务")
@RestController
@RequestMapping("/signp")
public class SignUpController {

	@Autowired
	private CredentialService credentialService;

	@Autowired
	private UserService userService;

	@RequestMapping("/sendSignUpSms")
	public Result<String> sendRegisterSms(String phoneNumber) {
		if (!PatternUtils.isPhoneNumber(phoneNumber)) {
			throw new ServiceException(UserErrorMessage.PHONE_NUMBER_ILLEGAL);
		}
		SmsNotification smsNotification = new SmsNotification();
		smsNotification.setReceiver(phoneNumber);
		smsNotification.setTemplateCode("");
		smsNotification.setSignName("");
		CredentialConfig config = CredentialConfig.builder()
				.principal(phoneNumber)
				.size(6)
				.type(CredentialType.NUMBER)
				.expire(Duration.ofMinutes(3L).toMillis())
				.application("sign_up").build();
		String token = credentialService.generate(config);
		return Result.success(token);
	}

	@RequestMapping("/signUpBySms")
	@ApiOperation(("通过短信注册"))
	public Result<String> signUpBySms(@RequestBody @Valid @ApiParam(name = "短信注册参数", required = true) SmsSignUpDto smsSignUpDto) {

		if (!PatternUtils.isPhoneNumber(smsSignUpDto.getPhoneNumber())) {
			throw new ServiceException(UserErrorMessage.PHONE_NUMBER_ILLEGAL);
		}

		CredentialValidation validation = CredentialValidation.builder()
				.application("sign_up")
				.principal(smsSignUpDto.getPhoneNumber())
				.token(smsSignUpDto.getToken())
				.credential(smsSignUpDto.getCredential())
				.ignoreCase(true)
				.build();

		//验证短信验证码
		ServiceAssert.isTrue(credentialService.validate(validation), UserErrorMessage.SMS_CREDENTIAL_NOT_MATCHED);

		UserAddDto userDto = UserAddDto.builder()
				.name("手机用户" + System.currentTimeMillis())
				.phoneNumber(smsSignUpDto.getPhoneNumber())
				.password(RandomUtils.generateNumber(8))
				.build();
		userService.add(userDto);

		return Result.success();
	}

	@RequestMapping("/signUp")
	@ApiOperation(("账号密码注册"))
	public Result<String> signUp(@RequestBody @Valid @ApiParam(name = "注册参数") UsernamePasswordSignUpDto usernamePasswordSignUpDto) {

		//验证确认密码是否匹配
		if (StringUtils.equals(usernamePasswordSignUpDto.getPassword(), usernamePasswordSignUpDto.getConfirmPassword())) {
			throw new ServiceException(UserErrorMessage.CONFIRM_PASSWORD_NOT_MATCHED);
		}

		UserAddDto userDto = UserAddDto.builder()
				.name(usernamePasswordSignUpDto.getName())
				.username(usernamePasswordSignUpDto.getUsername())
				.password(usernamePasswordSignUpDto.getPassword())
				.build();
		userService.add(userDto);
		return Result.success();
	}
}

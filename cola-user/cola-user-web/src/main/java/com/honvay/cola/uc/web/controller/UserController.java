package com.honvay.cola.uc.web.controller;

import com.honvay.cola.auth.core.model.AuthenticatedUser;
import com.honvay.cola.framework.core.ServiceAssert;
import com.honvay.cola.framework.core.exception.ServiceException;
import com.honvay.cola.framework.core.protocol.Result;
import com.honvay.cola.framework.util.PatternUtils;
import com.honvay.cola.notify.sms.api.SmsNotification;
import com.honvay.cola.sc.api.CredentialService;
import com.honvay.cola.sc.api.model.CredentialConfig;
import com.honvay.cola.sc.api.model.CredentialType;
import com.honvay.cola.sc.api.model.CredentialValidation;
import com.honvay.cola.uc.api.UserService;
import com.honvay.cola.uc.api.enums.UserErrorMessage;
import com.honvay.cola.uc.api.model.UserDto;
import com.honvay.cola.uc.api.model.UpdatePasswordDto;
import com.honvay.cola.uc.web.model.PhoneNumberBindDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Duration;

/**
 * @author LIQIU
 * created on 2018-11-16
 **/
@Api(description = "用户服务")
@RestController("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private CredentialService credentialService;

	@ApiOperation(value = "修改密码")
	@PostMapping("/updatePassword")
	public ResponseEntity<UserDto> updatePassword(@RequestBody @Valid @ApiParam("修改密码参数") UpdatePasswordDto updatePasswordDto,
												  @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {

		updatePasswordDto.setId(authenticatedUser.getId());
		userService.updatePassword(updatePasswordDto);

		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "绑定手机号码")
	@PostMapping("/bindPhoneNumber")
	public Result<String> bindPhoneNumber(@RequestBody @Valid @ApiParam("绑定手机号参数") PhoneNumberBindDto binding,
										  @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {

		CredentialValidation validation = CredentialValidation.builder()
				.application("sign_up")
				.principal(binding.getPhoneNumber())
				.token(binding.getToken())
				.credential(binding.getCredential())
				.ignoreCase(true)
				.build();
		//验证短信验证码
		ServiceAssert.isTrue(credentialService.validate(validation), UserErrorMessage.SMS_CREDENTIAL_NOT_MATCHED);

		this.userService.updatePhoneNumber(authenticatedUser.getId(), binding.getPhoneNumber());

		return Result.success();
	}

	@ApiOperation(value = "发送绑定手机号短信")
	@PostMapping("/sendBindingSms")
	public Result<String> sendBindingSms(String phoneNumber) {

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
				.application("bind_password").build();
		String token = credentialService.generate(config);
		return Result.success(token);
	}
}

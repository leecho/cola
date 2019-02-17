package com.honvay.cola.uc.provider.impl;

import com.honvay.cola.framework.core.ServiceAssert;
import com.honvay.cola.framework.core.exception.ServiceException;
import com.honvay.cola.sc.api.PasswordStrategy;
import com.honvay.cola.sc.api.exception.PasswordInvalidException;
import com.honvay.cola.uc.api.UserService;
import com.honvay.cola.uc.api.enums.UserErrorMessage;
import com.honvay.cola.uc.api.enums.UserStatus;
import com.honvay.cola.uc.api.model.UpdatePasswordDto;
import com.honvay.cola.uc.api.model.UserAddDto;
import com.honvay.cola.uc.api.model.UserDto;
import com.honvay.cola.uc.provider.domain.User;
import com.honvay.cola.uc.provider.event.PasswordChangedEvent;
import com.honvay.cola.uc.provider.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author LIQIU
 * created on 2018-11-16
 **/
@Service
public class UserServiceImpl implements UserService, ApplicationEventPublisherAware {

	/**
	 * 锁定用户的登录失败次数
	 */
	@Value("${spring.cola.user.loginFailTimesToLock:5}")
	private Integer loginFailTimesToLock;

	@Autowired
	private UserRepository userRepository;

	@Autowired(required = false)
	private PasswordStrategy passwordStrategy;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void updatePassword(UpdatePasswordDto updatePasswordDto) {

		this.checkPassword(updatePasswordDto.getNewPassword());

		User user = userRepository.findById(updatePasswordDto.getId()).orElseThrow(() -> new ServiceException(UserErrorMessage.USER_NOT_EXISTS));
		ServiceAssert.isTrue(passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword()), UserErrorMessage.ORIGIN_PASSWORD_NOT_MATCHED);
		user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
		this.userRepository.save(user);

		//广播事件，可以订阅来踢掉用户
		this.applicationEventPublisher.publishEvent(new PasswordChangedEvent(user.getUsername()));
	}

	@Override
	public void lock(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ServiceException(UserErrorMessage.USER_NOT_EXISTS));
		ServiceAssert.isTrue(user.getStatus().equals(UserStatus.LOCKED.getValue()), UserErrorMessage.USER_STATUS_ILLEGAL);
		user.setStatus(UserStatus.LOCKED.getValue());
		this.userRepository.save(user);
	}

	@Override
	public void unlock(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ServiceException(UserErrorMessage.USER_NOT_EXISTS));
		user.setStatus(UserStatus.ACTIVE.getValue());
		this.userRepository.save(user);
	}

	private void checkPassword(String password) {
		//密码策略验证
		if (passwordStrategy == null) {
			return;
		}
		try {
			passwordStrategy.check(password);
		} catch (PasswordInvalidException e) {
			throw new ServiceException(UserErrorMessage.PASSWORD_INVALID, e.getMessage());
		}
	}

	@Override
	public UserDto add(UserAddDto userAddDto) {

		this.checkPassword(userAddDto.getPassword());

		this.userRepository.findByUsername(userAddDto.getUsername()).ifPresent(exists -> {
			throw new ServiceException(UserErrorMessage.USER_NAME_EXISTS);
		});

		if (userAddDto.getEmail() != null) {
			this.userRepository.findByEmail(userAddDto.getEmail()).ifPresent(exists -> {
				throw new ServiceException(UserErrorMessage.USER_EMAIL_EXISTS);
			});
		}

		if (userAddDto.getPhoneNumber() != null) {
			this.userRepository.findByPhoneNumber(userAddDto.getPhoneNumber()).ifPresent(exists -> {
				throw new ServiceException(UserErrorMessage.USER_PHONE_NUMBER_EXISTS);
			});
		}
		userAddDto.setPassword(passwordEncoder.encode(userAddDto.getPassword()));

		User user = new User();
		BeanUtils.copyProperties(userAddDto, user);
		user = this.userRepository.save(user);

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		userDto.setId(user.getId());

		return userDto;
	}

	@Override
	public UserDto findByUsername(String username) {
		return this.userRepository.findByUsername(username).map(user -> {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			return userDto;
		}).orElse(null);
	}

	@Override
	public void processLoginFail(String username) {
		this.userRepository.findByUsername(username).ifPresent(user -> {
			Integer loginFailTimes = user.getLoginFailTimes();
			if (loginFailTimes == null) {
				loginFailTimes = 0;
			}
			loginFailTimes++;
			//超过限定次数则锁定用户
			if (loginFailTimes >= loginFailTimesToLock) {
				user.setStatus(UserStatus.LOCKED.getValue());
				user.setLoginFailTimes(0);
			} else {
				user.setLoginFailTimes(loginFailTimes);
			}
			this.userRepository.save(user);
		});
	}

	@Override
	public UserDto getById(Integer id) {
		return this.userRepository.findById(id).map(user -> {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			return userDto;
		}).orElseThrow(() -> new ServiceException(UserErrorMessage.USER_NOT_EXISTS));
	}

	@Override
	public void processLoginSuccess(String username, String loginIp, String client) {
		this.userRepository.findByUsername(username).ifPresent(user -> {
			user.setLastLoginDate(new Date());
			user.setLastLoginIp(loginIp);
			user.setLoginFailTimes(0);
			//TODO 调用其他API获取登录地址，获取可以改造成生成登录日志
			this.userRepository.save(user);
		});
	}

	@Override
	public UserDto findByPhoneNumber(String phoneNumber) {
		return this.userRepository.findByPhoneNumber(phoneNumber).map(user -> {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			return userDto;
		}).orElse(null);
	}

	@Override
	public void updatePhoneNumber(Integer id, String phoneNumber) {
		User user = this.userRepository.findById(id).orElseThrow(() -> new ServiceException(UserErrorMessage.USER_NOT_EXISTS));
		user.setPhoneNumber(phoneNumber);
		this.userRepository.save(user);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}

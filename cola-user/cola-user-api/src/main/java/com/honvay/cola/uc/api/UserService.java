package com.honvay.cola.uc.api;

import com.honvay.cola.uc.api.model.UpdatePasswordDto;
import com.honvay.cola.uc.api.model.UserAddDto;
import com.honvay.cola.uc.api.model.UserDto;

import java.util.Optional;

/**
 * @author LIQIU
 * created on 2018-11-16
 **/
public interface UserService {
	/**
	 * 修改用户密码
	 *
	 * @param updatePasswordDto 修改密码参数
	 */
	void updatePassword(UpdatePasswordDto updatePasswordDto);

	/**
	 * 锁定用户
	 *
	 * @param id 用户ID
	 */
	void lock(Integer id);

	/**
	 * 解锁用户
	 *
	 * @param id 用户ID
	 */
	void unlock(Integer id);

	/**
	 * 添加用户
	 *
	 * @param user 用户信息
	 * @return 添加后的用户
	 */
	UserDto add(UserAddDto user);

	/**
	 * 通过用户名查找用户
	 *
	 * @param username 用户名
	 * @return 用户
	 */
	UserDto findByUsername(String username);


	/**
	 * 处理用户登录失败
	 *
	 * @param username 用户名
	 */
	void processLoginFail(String username);

	/**
	 * 根据用户获取用户信息
	 *
	 * @param id 用户ID
	 * @return 用户信息
	 */
	UserDto getById(Integer id);

	/**
	 * 处理登录成功
	 *
	 * @param username 用户名
	 * @param loginIp  登录IP
	 * @param client   登录客户端
	 */
	void processLoginSuccess(String username, String loginIp, String client);

	/**
	 * 根据手机号获取用户
	 *
	 * @param phoneNumber
	 * @return
	 */
	UserDto findByPhoneNumber(String phoneNumber);

	/**
	 * 修改用户手机号码
	 *
	 * @param id          用户ID
	 * @param phoneNumber 手机号
	 */
	void updatePhoneNumber(Integer id, String phoneNumber);
}

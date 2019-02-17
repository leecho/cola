package com.honvay.cola.uc.provider.repository;

import com.honvay.cola.uc.provider.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author LIQIU
 * created on 2018-11-16
 **/
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * 通过用户名查找用户
	 *
	 * @param username 用户名
	 * @return 用户
	 */
	Optional<User> findByUsername(String username);

	/**
	 * 通过手机号查找用户
	 *
	 * @param phoneNumber 手机号码
	 * @return 用户
	 */
	Optional<User> findByPhoneNumber(String phoneNumber);

	/**
	 * 通过邮箱地址查找用户
	 *
	 * @param email 邮箱地址
	 * @return 用户
	 */
	Optional<User> findByEmail(String email);

}

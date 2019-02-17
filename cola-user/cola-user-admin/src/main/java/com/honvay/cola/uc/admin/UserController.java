package com.honvay.cola.uc.admin;

import com.honvay.cola.framework.core.protocol.Result;
import com.honvay.cola.uc.api.UserService;
import com.honvay.cola.uc.api.model.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
@Api("用户服务")
@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@ApiOperation(value = "获取用户信息")
	@GetMapping("/get")
	public Result<UserDto> get(@ApiParam(name = "用户ID", required = true) Integer id) {
		UserDto userDto = this.userService.getById(id);
		userDto.setPassword(null);
		return Result.success(userDto);
	}

	@ApiOperation(value = "锁定用户")
	@GetMapping("/lock")
	public Result<String> lock(@ApiParam(name = "用户ID", required = true) Integer id) {
		userService.lock(id);
		return Result.success();
	}

	@ApiOperation(value = "解锁用户")
	@GetMapping("/unlock")
	public Result<String> unlock(@ApiParam(name = "用户ID", required = true) Integer id) {
		userService.unlock(id);
		return Result.success();
	}


}

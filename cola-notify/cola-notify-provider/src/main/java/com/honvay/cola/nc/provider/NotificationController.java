package com.honvay.cola.nc.provider;

import com.honvay.cola.framework.core.protocol.Result;
import com.honvay.cola.nc.api.model.Notification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LIQIU
 * @date 2018-3-27
 **/
@Api(value = "通知中心")
public class NotificationController {

	@Autowired
	private NotificationDispatcher dispatcher;

	@ApiOperation("/短信通知")
	@RequestMapping("/notify")
	public Result<String> notify(Notification notification) {
		this.dispatcher.dispatch(notification);
		return Result.success();
	}
}

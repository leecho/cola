package com.honvay.cola.nc.api;

import com.honvay.cola.nc.api.model.Notification;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
public interface NotificationService {

	/**
	 * 通知
	 *
	 * @param notification 通知
	 */
	void notify(Notification notification);

}

package com.honvay.cola.nc.api.exchanger;


import com.honvay.cola.nc.api.model.Notification;

/**
 * @author LIQIU
 * @date 2018-3-27
 **/
public interface NotificationExchanger<T extends Notification> {


	/**
	 * 通知
	 *
	 * @param notification 路由通知
	 * @return
	 */
	boolean exchange(T notification);
}

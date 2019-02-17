package com.honvay.cola.nc.provider;


import com.honvay.cola.nc.api.model.Notification;
import com.honvay.cola.nc.api.exchanger.NotificationExchanger;

import java.util.concurrent.Callable;

/**
 * @author LIQIU
 * @date 2018-3-31
 **/
public class NotificationTask<T extends Notification> implements Callable<Boolean> {

	private NotificationExchanger<T> notificationExchanger;

	private T notification;

	public NotificationTask(NotificationExchanger<T> notificationExchanger, T notification) {
		this.notificationExchanger = notificationExchanger;
		this.notification = notification;
	}

	@Override
	public Boolean call() {
		return notificationExchanger.exchange(notification);
	}
}

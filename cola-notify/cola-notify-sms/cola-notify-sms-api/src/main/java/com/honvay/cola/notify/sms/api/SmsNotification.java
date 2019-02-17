package com.honvay.cola.notify.sms.api;

import com.honvay.cola.nc.api.model.Notification;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LIQIU
 * @date 2018-3-27
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SmsNotification extends Notification {

	private String templateCode;

	private String signName;

}

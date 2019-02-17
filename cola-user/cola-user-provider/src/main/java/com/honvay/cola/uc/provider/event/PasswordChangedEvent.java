package com.honvay.cola.uc.provider.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
public class PasswordChangedEvent extends ApplicationEvent {
	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public PasswordChangedEvent(Object source) {
		super(source);
	}
}

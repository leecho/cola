package com.honvay.cola.nc.provider;

import com.honvay.cola.nc.api.model.Notification;
import com.honvay.cola.nc.api.exchanger.NotificationExchanger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author LIQIU
 * @date 2018-3-27
 **/
@Component
@Slf4j
public class NotificationDispatcher {

	private Map<Class<?>, NotificationExchanger> exchangers;

	private ExecutorService executorService;

	public NotificationDispatcher() {
		int numOfThreads = Runtime.getRuntime().availableProcessors() * 2;
		executorService = new ThreadPoolExecutor(numOfThreads,
				numOfThreads,
				0,
				TimeUnit.MILLISECONDS,
				new LinkedBlockingDeque<>(),
				new NotificationThreadFactory());
		log.info("Init Notification ExecutorService , numOfThread : " + numOfThreads);
	}

	/**
	 * 分发通知
	 *
	 * @param notification 通知参数
	 */
	public void dispatch(Notification notification) {
		if (notification != null && exchangers != null) {
			NotificationExchanger<?> exchanger = this.exchangers.get(notification.getClass());

			if (exchanger == null) {
				throw new UnsupportedOperationException("Unsupported notification type: " + notification.getClass());
			}
			executorService.submit(new NotificationTask(exchanger, notification));
		}
	}

	@Autowired(required = false)
	private void initExchangers(List<NotificationExchanger> exchangers) {
		this.exchangers = new HashMap<>(exchangers.size());
		exchangers.forEach(exchanger -> {
			ResolvableType resolvableType = ResolvableType.forClass(exchanger.getClass());
			Class<?> notificationClass = resolvableType.getGeneric(0).resolve();
			this.exchangers.put(notificationClass, exchanger);
		});
	}
}

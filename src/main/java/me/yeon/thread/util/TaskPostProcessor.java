package me.yeon.thread.util;

import static me.yeon.thread.util.AppConstants.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import lombok.RequiredArgsConstructor;
import me.yeon.thread.dto.ThreadStatus;

@RequiredArgsConstructor
public class TaskPostProcessor implements BeanPostProcessor {

	private final SimpMessageSendingOperations operations;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof ThreadPoolTaskScheduler) {
			return new ThreadPoolTaskSchedulerProxy(operations);
		}

		return bean;
	}

	private static class ThreadPoolTaskSchedulerProxy extends ThreadPoolTaskScheduler {

		private final SimpMessageSendingOperations operations;

		public ThreadPoolTaskSchedulerProxy(SimpMessageSendingOperations operations) {
			this.operations = operations;
		}

		@Override
		protected void beforeExecute(Thread thread, Runnable task) {
			super.beforeExecute(thread, task);

			String threadName = thread.getThreadGroup().getName() + " > ID: " + thread.getId();
			LocalDateTime now = LocalDateTime.now();
			String content = "Thread Group " + threadName + ") activeCount: " + thread.getThreadGroup().activeCount()
				+ " started at " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-ss hh:mm:ss")) + ".";
			operations.convertAndSend("/sub/rooms/" + DEFAULT_ROOM_CODE, new ThreadStatus(content));
		}

		@Override
		protected void afterExecute(Runnable task, Throwable ex) {
			super.afterExecute(task, ex);

			Thread thread = Thread.currentThread();
			String threadName = thread.getThreadGroup().getName() + " > ID: " + thread.getId();
			LocalDateTime now = LocalDateTime.now();
			String content = "Thread Group " + threadName + ") activeCount: " + thread.getThreadGroup().activeCount()
				+ " finished at " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-ss hh:mm:ss")) + ".";
			operations.convertAndSend("/sub/rooms/" + DEFAULT_ROOM_CODE, new ThreadStatus(content));
		}
	}

}

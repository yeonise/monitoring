package me.yeon.thread.config;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import lombok.RequiredArgsConstructor;
import me.yeon.thread.util.TaskPostProcessor;

@Configuration
@RequiredArgsConstructor
public class BeanPostProcessorConfig implements BeanPostProcessor {

	private final SimpMessageSendingOperations operations;

	@Bean
	public BeanPostProcessor beanPostProcessor() {
		return new TaskPostProcessor(operations);
	}

}

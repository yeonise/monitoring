package me.yeon.thread.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(value = {
	CookieProperties.class
})
public class PropertiesConfig {

}

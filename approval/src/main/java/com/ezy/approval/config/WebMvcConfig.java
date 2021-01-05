package com.ezy.approval.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: zehao.zhang
 * @CreateDate: 2020/07/08 14:33
 * @Desc
 * @Version: 1.0
 */
@Configuration
public class WebMvcConfig {
	@Bean
	public WebMvcConfigurer configurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("*")
						.allowCredentials(true)
						.allowedOrigins("*");
			}
		};
	}

}

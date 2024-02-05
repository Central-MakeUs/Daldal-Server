package com.mm.coreinfrafeign.oauth.config;

import org.springframework.context.annotation.Bean;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;

public class KakaoAuthFeignConfig {
	@Bean
	public RequestInterceptor requestInterceptor() {
		return template -> template.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new FeignClientExceptionErrorDecoder();
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}

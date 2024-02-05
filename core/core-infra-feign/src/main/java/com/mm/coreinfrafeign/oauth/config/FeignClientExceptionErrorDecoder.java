package com.mm.coreinfrafeign.oauth.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() >= 400 && response.status() <= 499) {
			log.error("{}번 에러 발생 : {}", response.status(), response.reason());
			return new RuntimeException("Feign Error Detected");
		} else {
			log.error("500번대 에러 발생 : {}", response.reason());
			return new RuntimeException("Feign Error Detected");
		}
	}
}

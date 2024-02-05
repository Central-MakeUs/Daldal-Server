package com.mm.coreinfrafeign.oauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mm.coreinfrafeign.oauth.config.KakaoAuthFeignConfig;
import com.mm.coreinfrafeign.oauth.dto.response.KakaoAuthInfoResponse;

@FeignClient(name = "KakaoAuthInfoClient", url = "https://kapi.kakao.com/v2/user/me", configuration = KakaoAuthFeignConfig.class)
public interface KakaoAuthInfoClient {
	@GetMapping
	KakaoAuthInfoResponse getInfo(@RequestHeader(name = "Authorization") String accessToken);
}

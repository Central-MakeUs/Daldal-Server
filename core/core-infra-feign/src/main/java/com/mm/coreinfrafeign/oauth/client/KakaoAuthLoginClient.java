package com.mm.coreinfrafeign.oauth.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

import com.mm.coreinfrafeign.oauth.config.KakaoAuthFeignConfig;
import com.mm.coreinfrafeign.oauth.dto.response.KakaoAuthLoginResponse;

@FeignClient(name = "KakaoAuthLoginClient", url = "https://kauth.kakao.com/oauth/token", configuration = KakaoAuthFeignConfig.class)
public interface KakaoAuthLoginClient {
	@PostMapping
	KakaoAuthLoginResponse getAccessToken(@SpringQueryMap Map<String, Object> parameters);
}

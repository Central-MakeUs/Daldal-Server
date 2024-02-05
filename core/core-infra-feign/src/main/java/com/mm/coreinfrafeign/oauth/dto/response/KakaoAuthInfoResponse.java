package com.mm.coreinfrafeign.oauth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

public record KakaoAuthInfoResponse(
	String id,
	@JsonProperty("kakao_account") KakaoAccount kakaoAccount) {
	@Getter
	public static class KakaoAccount {
		private String email;
	}
}

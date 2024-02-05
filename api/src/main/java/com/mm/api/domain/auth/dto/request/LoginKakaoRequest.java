package com.mm.api.domain.auth.dto.request;

public record LoginKakaoRequest(String redirectUri, String code) {
}

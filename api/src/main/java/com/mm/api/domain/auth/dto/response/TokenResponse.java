package com.mm.api.domain.auth.dto.response;

public record TokenResponse(String accessToken, String refreshToken) {
}

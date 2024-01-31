package com.mm.api.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record RefreshTokenRequest(@Schema(description = "Bearer 를 붙이지 말아주세요")
                                  String refreshToken) {
}

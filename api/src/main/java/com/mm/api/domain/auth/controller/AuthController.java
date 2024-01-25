package com.mm.api.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.domain.auth.dto.request.RefreshTokenRequest;
import com.mm.api.domain.auth.dto.response.TokenResponse;
import com.mm.api.domain.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "회원 인증", description = "회원 인증 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@Operation(summary = "oAuth 로그인을 합니다. 현재 provider는 kakao만 제공됩니다.")
	@GetMapping("/oauth2/authorization/{oauth2-provider}")
	public void login() {
		// oauth2 로그인
	}

	@Operation(summary = "")
	@PostMapping("/oauth2/logout")
	public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest request) {
		authService.logout(request);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "access token을 갱신합니다.")
	@PostMapping("/api/v1/auth/refresh-access-token")
	public ResponseEntity<TokenResponse> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
		TokenResponse tokenResponse = authService.refreshAccessToken(request);
		return ResponseEntity.ok(tokenResponse);
	}
}

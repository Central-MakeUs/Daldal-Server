package com.mm.api.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.domain.auth.dto.request.RefreshTokenRequest;
import com.mm.api.domain.auth.dto.response.TokenResponse;
import com.mm.api.domain.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@GetMapping("/oauth2/authorization/{oauth2-provider}")
	public void login() {
		// oauth2 로그인
	}

	@PostMapping("/api/v1/auth/refresh-access-token")
	public ResponseEntity<TokenResponse> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
		TokenResponse tokenResponse = authService.refreshAccessToken(request);
		return ResponseEntity.ok(tokenResponse);
	}
}

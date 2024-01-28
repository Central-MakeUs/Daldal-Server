package com.mm.api.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.domain.auth.dto.request.RefreshTokenRequest;
import com.mm.api.domain.auth.dto.response.TokenResponse;
import com.mm.api.domain.auth.service.AuthService;
import com.mm.api.domain.member.dto.response.MemberInfoResponse;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "회원 인증", description = "회원 인증 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@Operation(summary = "로그아웃 합니다.")
	@PostMapping("/api/v1/auth/logout")
	public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest request) {
		authService.logout(request);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "로그인된 사용자 정보를 가져옵니다.")
	@GetMapping("/api/v1/auth/me")
	public ResponseEntity<?> getMe(@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		MemberInfoResponse response = authService.getMe(userDetails);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "access token을 갱신합니다.")
	@PostMapping("/api/v1/auth/refresh-access-token")
	public ResponseEntity<TokenResponse> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
		TokenResponse tokenResponse = authService.refreshAccessToken(request);
		return ResponseEntity.ok(tokenResponse);
	}
}

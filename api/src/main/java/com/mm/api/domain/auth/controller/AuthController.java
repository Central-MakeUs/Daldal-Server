package com.mm.api.domain.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.common.swaggerAnnotation.SwaggerResponseAuth;
import com.mm.api.domain.auth.dto.request.LoginKakaoRequest;
import com.mm.api.domain.auth.dto.request.RefreshTokenRequest;
import com.mm.api.domain.auth.dto.response.TokenResponse;
import com.mm.api.domain.auth.service.AuthService;
import com.mm.api.domain.member.dto.response.MemberInfoResponse;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "회원 인증", description = "회원 인증 관련 API 입니다.")
@SwaggerResponseAuth
@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@Operation(summary = "(구 방식) 소셜 로그인을 합니다. /oauth2/authorization/{provider}", description = "/oauth2/authorization/{provider}  provider는 KAKAO, APPLE")
	@GetMapping("/dummy")
	public void dummy(@RequestBody LoginKakaoRequest request) {
	}

	@GetMapping("/super-token")
	public TokenResponse getSuperToken() {
		TokenResponse superToken = authService.getSuperToken();
		return superToken;
	}

	@Operation(summary = "(신 방식) 카카오 소셜 로그인을 합니다.")
	@GetMapping("/api/v1/auth/login/kakao")
	public CommonResponse<TokenResponse> loginKakao(@RequestBody LoginKakaoRequest request) {
		TokenResponse response = authService.loginKakao(request);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "로그아웃 합니다.")
	@PostMapping("/api/v1/auth/logout")
	public CommonResponse<?> logout(@RequestBody RefreshTokenRequest request) {
		authService.logout(request);
		return CommonResponse.noContent();
	}

	@Operation(summary = "로그인된 사용자 정보를 가져옵니다.")
	@GetMapping("/api/v1/auth/me")
	public CommonResponse<MemberInfoResponse> getMe(@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		MemberInfoResponse response = authService.getMe(userDetails);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "회원탈퇴를 합니다.")
	@DeleteMapping("/api/v1/auth/withdrawal")
	public CommonResponse<MemberInfoResponse> getWithdrawl(@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		authService.getWithdrawl(userDetails);
		return CommonResponse.noContent();
	}

	@Operation(summary = "access token을 갱신합니다.", description = "Bearer 를 붙이지 말아주세요")
	@PostMapping("/api/v1/auth/refresh-access-token")
	public CommonResponse<TokenResponse> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
		TokenResponse tokenResponse = authService.refreshAccessToken(request);
		return CommonResponse.created(tokenResponse);
	}
}

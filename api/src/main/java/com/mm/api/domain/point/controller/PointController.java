package com.mm.api.domain.point.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.common.swaggerAnnotation.SwaggerResponsePoint;
import com.mm.api.domain.point.dto.response.PointsResponse;
import com.mm.api.domain.point.service.PointService;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "포인트", description = "포인트 관련 API 입니다.")
@SwaggerResponsePoint
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PointController {
	private final PointService pointService;

	// 유저
	@Operation(summary = "현재 사용자의 포인트를 가져옵니다.")
	@GetMapping("/points/me")
	public CommonResponse<Integer> getMyPoint(@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		Integer response = pointService.getMyPoint(userDetails);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "현재 사용자의 누적 포인트 내역을 가져옵니다.")
	@GetMapping("/points/history/cumulate")
	public CommonResponse<PointsResponse> getCumulativeHistory(
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		PointsResponse response = pointService.getCumulativeHistory(userDetails);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "현재 사용자의 예상 포인트 내역을 가져옵니다.")
	@GetMapping("/points/history/expect")
	public CommonResponse<PointsResponse> getExpectedHistory(
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		PointsResponse response = pointService.getExpectedHistory(userDetails);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "포인트 출금을 신청합니다.")
	@PostMapping("/points/withdraw")
	public CommonResponse postPointsWithdraw(@RequestParam Integer refund,
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		pointService.postPointsWithdraw(userDetails, refund);
	}
}

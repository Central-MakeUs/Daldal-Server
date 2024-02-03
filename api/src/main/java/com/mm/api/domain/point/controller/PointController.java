package com.mm.api.domain.point.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.common.swaggerAnnotation.SwaggerErrorsPoint;
import com.mm.api.domain.buy.dto.response.BuyResponse;
import com.mm.api.domain.point.service.PointService;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "포인트", description = "포인트 관련 API 입니다.")
@SwaggerErrorsPoint
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PointController {
	private final PointService pointService;

	@Operation(summary = "현재 사용자의 포인트를 가져옵니다.")
	@GetMapping("/points/me")
	public CommonResponse<Integer> getMyPoint(@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		Integer response = pointService.getMyPoint(userDetails);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "현재 사용자의 누적 포인트 내역을 가져옵니다.")
	@GetMapping("/points/history/cumulate")
	public CommonResponse<?> getCumulativeHistory(@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		List<BuyResponse> responses = pointService.getCumulativeHistory(userDetails);
		return CommonResponse.ok(responses);
	}

	@Operation(summary = "현재 사용자의 예상 포인트 내역을 가져옵니다.")
	@GetMapping("/points/history/expect")
	public CommonResponse<?> getExpectedHistory(@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		List<BuyResponse> responses = pointService.getExpectedHistory(userDetails);
		return CommonResponse.ok(responses);
	}
}

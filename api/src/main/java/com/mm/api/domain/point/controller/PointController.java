package com.mm.api.domain.point.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.domain.buy.dto.response.BuyResponse;
import com.mm.api.domain.point.service.PointService;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PointController {
	private final PointService pointService;

	@GetMapping("/points/me")
	public CommonResponse<Integer> getMyPoint(@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		Integer response = pointService.getMyPoint(userDetails);
		return CommonResponse.ok(response);
	}

	@GetMapping("/points/history/cumulate")
	public CommonResponse<?> getCumulativeHistory(@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		List<BuyResponse> responses = pointService.getCumulativeHistory(userDetails);
		return CommonResponse.ok(responses);
	}

	@GetMapping("/points/history/expect")
	public CommonResponse<?> getExpectedHistory(@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		List<BuyResponse> responses = pointService.getExpectedHistory(userDetails);
		return CommonResponse.ok(responses);
	}
}

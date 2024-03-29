package com.mm.api.domain.buy.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.common.swaggerAnnotation.SwaggerResponseBuy;
import com.mm.api.domain.buy.dto.response.BuyMeListResponse;
import com.mm.api.domain.buy.dto.response.BuyResponse;
import com.mm.api.domain.buy.service.BuyService;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "구매 인증", description = "구매 인증 관련 API 입니다.")
@SwaggerResponseBuy
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BuyController {
	private final BuyService buyService;

	// 관리자만
	@Operation(summary = "구매 인증 상태를 변경합니다.", description = "refundStatus = [IN_PROGRESS, COMPLETED, REJECTED]")
	@PatchMapping("/buys/{buyId}/refund-status")
	public CommonResponse<BuyResponse> updateBuyRefundStatus(@PathVariable Long buyId,
		@RequestParam String refundStatus) {
		BuyResponse response = buyService.updateBuyRefundStatus(buyId, refundStatus);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "구매 인증을 삭제합니다.")
	@DeleteMapping("/buys/{buyId}")
	public CommonResponse<?> deleteBuy(@PathVariable Long buyId,
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		buyService.deleteBuy(buyId);
		return CommonResponse.noContent();
	}

	@Operation(summary = "구매 인증 단건을 조회합니다.")
	@GetMapping("/buys/{buyId}")
	public CommonResponse<BuyResponse> getBuy(@PathVariable Long buyId,
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		BuyResponse response = buyService.getBuyResponse(buyId);
		return CommonResponse.ok(response);
	}

	// 회원만
	@Operation(summary = "구매 인증을 작성합니다.", description = "form으로 input type을 file로 지정해서 이미지를 첨부합니다.")
	@PostMapping(value = "/buys", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public CommonResponse<BuyResponse> postBuy(@AuthenticationPrincipal OAuth2UserDetails userDetails,
		@RequestPart(value = "file", required = true) MultipartFile file) {
		BuyResponse buyResponse = buyService.postBuy(userDetails, file);
		return CommonResponse.ok(buyResponse);
	}

	@Operation(summary = "내 구매 인증을 페이지 단위로 가져옵니다.")
	@GetMapping("/buys/me")
	public CommonResponse<BuyMeListResponse> getBuysMe(@RequestParam(required = false, defaultValue = "1") Integer page,
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		BuyMeListResponse responses = buyService.getBuysMe(page, userDetails);
		return CommonResponse.ok(responses);
	}
}

package com.mm.api.domain.buy.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mm.api.domain.buy.dto.request.RejectBuyRefundStatusRequest;
import com.mm.api.domain.buy.dto.response.BuyResponse;
import com.mm.api.domain.buy.service.BuyService;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "구매 인증", description = "구매 인증 관련 API 입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BuyController {
	private final BuyService buyService;

	// 관리자만
	@Operation(summary = "전체 구매 인증을 페이지 단위로 가져옵니다.")
	@GetMapping("/buys")
	public ResponseEntity<?> getBuys(@RequestParam(required = false, defaultValue = "1") Integer page) {
		List<BuyResponse> responses = buyService.getBuys(page);
		return ResponseEntity.ok(responses);
	}

	@Operation(summary = "구매 인증 상태를 변경합니다.")
	@PatchMapping("/buys/{buyId}/refund-status")
	public ResponseEntity<?> updateBuyRefundStatus(@PathVariable Long buyId,
		@RequestParam String refundStatus) {
		BuyResponse response = buyService.updateBuyRefundStatus(buyId, refundStatus);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "구매 인증을 미승인 처리합니다.")
	@PatchMapping("/buys/{buyId}/refund-status")
	public ResponseEntity<?> rejectBuyRefundStatus(@PathVariable Long buyId,
		@RequestBody RejectBuyRefundStatusRequest request) {
		BuyResponse response = buyService.rejectBuyRefundStatus(buyId, request);
		return ResponseEntity.ok(response);
	}

	// 관리자 + 회원(자신만)
	@Operation(summary = "구매 인증을 삭제합니다.")
	@DeleteMapping("/buys/{buyId}")
	public ResponseEntity<?> deleteBuy(@PathVariable Long buyId) {
		buyService.deleteBuy(buyId);
		return ResponseEntity.noContent().build();
	}

	// 회원만
	@Operation(summary = "구매 인증을 작성합니다.")
	@PostMapping("/buys/{memberId}/{itemId}")
	public ResponseEntity<?> postBuy(@PathVariable Long memberId, @PathVariable Long itemId,
		@RequestPart(value = "file", required = true) MultipartFile file) {
		BuyResponse buyResponse = buyService.postBuy(memberId, itemId, file);
		return ResponseEntity.ok(buyResponse);
	}

	@Operation(summary = "내 구매 인증을 전부 가져옵니다.")
	@GetMapping("/buys/me")
	public ResponseEntity<?> getBuysMe(@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		List<BuyResponse> responses = buyService.getBuysMe(userDetails);
		return ResponseEntity.ok(responses);
	}
}

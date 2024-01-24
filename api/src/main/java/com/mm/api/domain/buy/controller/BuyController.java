package com.mm.api.domain.buy.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

import com.mm.api.domain.buy.dto.response.BuyResponse;
import com.mm.api.domain.buy.service.BuyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BuyController {
	private final BuyService buyService;

	// 관리자만
	@GetMapping("/buys")
	public ResponseEntity<?> getBuys(@RequestParam(required = false, defaultValue = "1") Integer page) {
		List<BuyResponse> responses = buyService.getBuys(page);
		return ResponseEntity.ok(responses);
	}

	@PatchMapping("/buys/{buyId}/refund-status")
	public ResponseEntity<?> updateBuyRefundStatus(@PathVariable Long buyId,
		@RequestParam String refundStatus) {
		BuyResponse response = buyService.updateBuyRefundStatus(buyId, refundStatus);
		return ResponseEntity.ok(response);
	}

	// 관리자 + 회원(자신만)
	@DeleteMapping("/buys/{buyId}")
	public ResponseEntity<?> deleteBuy(@PathVariable Long buyId) {
		buyService.deleteBuy(buyId);
		return ResponseEntity.noContent().build();
	}

	// 회원만
	@PostMapping("/buys/{memberId}/{itemId}")
	public ResponseEntity<?> postBuy(@PathVariable Long memberId, @PathVariable Long itemId,
		@RequestPart(value = "file", required = true) MultipartFile file) {
		BuyResponse buyResponse = buyService.postBuy(memberId, itemId, file);
		return ResponseEntity.ok(buyResponse);
	}
}

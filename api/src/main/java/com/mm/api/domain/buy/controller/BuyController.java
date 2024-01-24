package com.mm.api.domain.buy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mm.api.domain.buy.service.BuyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BuyController {
	private final BuyService buyService;

	// 관리자만
	@GetMapping("/buys")
	public void getBuys(@RequestParam(required = false, defaultValue = "1") Integer page) {

	}

	@PatchMapping("/buys/{buyId}/refund-status")
	public void updateBuyRefundStatus(@RequestParam String refundStatus) {

	}

	// 회원만
	@PostMapping("/buys/{memberId}/{itemId}")
	public void postBuy(@PathVariable Long memberId, @PathVariable Long itemId,
		@RequestPart(value = "file", required = true) MultipartFile file) {
		buyService.postBuy(memberId, itemId, file);

	}
}

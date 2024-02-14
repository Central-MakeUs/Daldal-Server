package com.mm.api.domain.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.domain.admin.dto.request.RejectBuyRefundStatusRequest;
import com.mm.api.domain.admin.dto.response.AdminItemListResponse;
import com.mm.api.domain.admin.dto.response.BuyListResponse;
import com.mm.api.domain.admin.dto.response.WithdrawListResponse;
import com.mm.api.domain.admin.service.AdminService;
import com.mm.api.domain.buy.dto.response.BuyResponse;
import com.mm.api.domain.item.dto.response.ItemDetailResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "어드민", description = "어드민 관련 API 입니다.")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;

	@Operation(summary = "url을 입력해 상품글을 크롤링합니다.", description = "zigzag url만 가능, ex) https://s.zigzag.kr/dV7jnGouAl?af=1")
	@PostMapping("/items/crawl")
	public CommonResponse<ItemDetailResponse> crawlItem(@RequestParam String url) {
		ItemDetailResponse response = adminService.crawlItem(url);
		return CommonResponse.created(response);
	}

	@Operation(summary = "크롤링으로 생성된 상품글에 video url을 추가합니다")
	@PatchMapping("/items/{itemId}/video-url")
	public CommonResponse<?> updateVideoUrl(@PathVariable Long itemId,
		@RequestParam String url) {
		adminService.updateVideoUrl(itemId, url);
		return CommonResponse.noContent();
	}

	@Operation(summary = "관리자 페이지 표시용 상품 데이터를 가져옵니다")
	@GetMapping("/items")
	public CommonResponse<AdminItemListResponse> getItemsForAdmin(
		@RequestParam(required = false, defaultValue = "1") Integer page) {
		AdminItemListResponse response = adminService.getItems(page);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "특정 상품을 추천 상품으로 등록합니다")
	@PatchMapping("/items/{itemId}/suggest")
	public CommonResponse<?> updateItemSuggest(@PathVariable Long itemId) {
		adminService.updateItemSuggest(itemId);
		return CommonResponse.noContent();
	}

	@Operation(summary = "특정 상품을 추천 상품에서 해제합니다")
	@PatchMapping("/items/{itemId}/not-suggest")
	public CommonResponse<?> updateItemNotSuggest(@PathVariable Long itemId) {
		adminService.updateItemNotSuggest(itemId);
		return CommonResponse.noContent();
	}

	@Operation(summary = "전체 구매 인증을 페이지 단위로 가져옵니다")
	@GetMapping("/buys")
	public CommonResponse<BuyListResponse> getBuys(@RequestParam(required = false, defaultValue = "1") Integer page) {
		BuyListResponse responses = adminService.getBuys(page);
		return CommonResponse.ok(responses);
	}

	@Operation(summary = "구매 인증 결제액을 설정합니다")
	@PatchMapping("/buys/{buyId}/purchase-amount")
	public CommonResponse<BuyResponse> setBuyPurchaseAmount(@PathVariable Long buyId,
		@RequestParam Integer purchase) {
		BuyResponse response = adminService.setBuyPurchaseAmount(buyId, purchase);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "구매 인증을 승인 처리합니다", description = "승인 처리 전 결제액을 설정해야합니다.")
	@PatchMapping("/buys/{buyId}/approve")
	public CommonResponse<BuyResponse> approveBuyRefundStatus(@PathVariable Long buyId) {
		BuyResponse response = adminService.approveBuyRefundStatus(buyId);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "구매 인증을 미승인 처리합니다", description = "미승인 사유를 입력해주세요.")
	@PatchMapping("/buys/{buyId}/reject")
	public CommonResponse<BuyResponse> rejectBuyRefundStatus(@PathVariable Long buyId,
		@RequestBody RejectBuyRefundStatusRequest request) {
		BuyResponse response = adminService.rejectBuyRefundStatus(buyId, request);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "전체 출금 신청을 페이지 단위로 가져옵니다")
	@GetMapping("/buys/withdraw")
	public CommonResponse<WithdrawListResponse> getWithdraws(
		@RequestParam(required = false, defaultValue = "1") Integer page) {
		WithdrawListResponse response = adminService.getWithdraws(page);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "특정 유저 출금 신청을 페이지 단위로 가져옵니다")
	@GetMapping("/buys/withdraw/members/{memberId}")
	public CommonResponse<WithdrawListResponse> getWithdraws(
		@RequestParam(required = false, defaultValue = "1") Integer page,
		@PathVariable Long memberId) {
		WithdrawListResponse response = adminService.getWithdrawsByMember(page, memberId);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "출금 신청을 승인 처리합니다")
	@PatchMapping("/buys/{buyId}/withdraw/approve")
	public CommonResponse<BuyResponse> approvePointsWithdraw(@PathVariable Long buyId) {
		BuyResponse response = adminService.approvePointsWithdraw(buyId);
		return CommonResponse.ok(response);
	}

	@Operation(summary = "출금 신청을 미승인 처리합니다")
	@PatchMapping("/buys/{buyId}/withdraw/reject")
	public CommonResponse<BuyResponse> rejectPointsWithdraw(@PathVariable Long buyId,
		@RequestBody RejectBuyRefundStatusRequest request) {
		BuyResponse response = adminService.rejectPointsWithdraw(buyId, request);
		return CommonResponse.ok(response);
	}
}

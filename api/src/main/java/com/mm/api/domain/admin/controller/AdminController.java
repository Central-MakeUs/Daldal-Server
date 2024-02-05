package com.mm.api.domain.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.domain.admin.dto.response.AdminItemListResponse;
import com.mm.api.domain.admin.service.AdminService;
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
}

package com.mm.api.domain.admin.controller;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.domain.admin.dto.response.AdminItemListResponse;
import com.mm.api.domain.admin.service.AdminService;
import com.mm.api.domain.item.dto.response.ItemDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}

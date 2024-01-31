package com.mm.api.domain.item.controller;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.domain.item.dto.request.ItemCreateRequest;
import com.mm.api.domain.item.dto.request.ItemUpdateRequest;
import com.mm.api.domain.item.dto.response.ItemDetailResponse;
import com.mm.api.domain.item.dto.response.ItemResponse;
import com.mm.api.domain.item.service.ItemService;
import com.mm.coresecurity.oauth.OAuth2UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "상품", description = "상품 관련 API 입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 관리자 권한
    @Operation(summary = "상품 글을 작성합니다.")
    @PostMapping("/items")
    public CommonResponse<?> createItem(@RequestBody ItemCreateRequest request) {
        ItemResponse response = itemService.createItem(request);
        return CommonResponse.ok(response);
    }

    @Operation(summary = "상품 글을 업데이트합니다.")
    @PutMapping("/items/{id}")
    public CommonResponse<?> updateItem(@RequestParam Long id, @RequestBody ItemUpdateRequest request) {
        ItemResponse response = itemService.updateItem(id, request);
        return CommonResponse.ok(response);
    }

    @Operation(summary = "상품 글을 삭제합니다.")
    @DeleteMapping("/items/{id}")
    public CommonResponse<?> deleteItem(@RequestParam Long id) {
        itemService.deleteItem(id);
        return CommonResponse.noContent();
    }

    // 권한 X
    @Operation(summary = "상품 글을 페이지 단위로 읽어옵니다.")
    @GetMapping("/items")
    public CommonResponse<?> getItems(@RequestParam(required = false, defaultValue = "1") Integer page,
                                      @RequestParam(required = false) String itemCategoryType,
                                      @AuthenticationPrincipal OAuth2UserDetails userDetails) {
        List<ItemResponse> responses = itemService.getItems(page, userDetails, itemCategoryType);
        return CommonResponse.ok(responses);
    }

    @Operation(summary = "상품 글의 상세 내용한을 읽어옵니다.")
    @GetMapping("/items/{id}")
    public CommonResponse<?> getItemDetail(@RequestParam Long id,
                                           @AuthenticationPrincipal OAuth2UserDetails userDetails) {
        ItemDetailResponse response = itemService.getItemDetail(id, userDetails);
        return CommonResponse.ok(response);
    }
}

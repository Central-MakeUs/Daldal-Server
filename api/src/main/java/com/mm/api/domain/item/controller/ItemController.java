package com.mm.api.domain.item.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.domain.item.dto.request.ItemCreateRequest;
import com.mm.api.domain.item.dto.request.ItemUpdateRequest;
import com.mm.api.domain.item.dto.response.ItemDetailResponse;
import com.mm.api.domain.item.dto.response.ItemResponse;
import com.mm.api.domain.item.service.ItemService;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "상품", description = "상품 관련 API 입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;

	// 관리자 권한
	@Operation(summary = "상품 글을 작성합니다.")
	@PostMapping("/items")
	public ResponseEntity<?> createItem(@RequestBody ItemCreateRequest request) {
		ItemResponse response = itemService.createItem(request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "상품 글을 업데이트합니다.")
	@PutMapping("/items/{id}")
	public ResponseEntity<?> updateItem(@RequestParam Long id, @RequestBody ItemUpdateRequest request) {
		ItemResponse response = itemService.updateItem(id, request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "상품 글을 삭제합니다.")
	@DeleteMapping("/items/{id}")
	public ResponseEntity<?> deleteItem(@RequestParam Long id) {
		itemService.deleteItem(id);
		return ResponseEntity.noContent().build();
	}

	// 권한 X
	@Operation(summary = "상품 글을 페이지 단위로 읽어옵니다.")
	@GetMapping("/items")
	public ResponseEntity<?> getItems(@RequestParam(required = false, defaultValue = "1") Integer page,
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		List<ItemResponse> responses = itemService.getItems(page, userDetails);
		return ResponseEntity.ok(responses);
	}

	@Operation(summary = "상품 글의 상세 내용을 읽어옵니다.")
	@GetMapping("/items/{id}")
	public ResponseEntity<?> getItemDetail(@RequestParam Long id,
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		ItemDetailResponse response = itemService.getItemDetail(id, userDetails);
		return ResponseEntity.ok(response);
	}
}

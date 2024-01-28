package com.mm.api.domain.dib.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.domain.dib.service.DibService;
import com.mm.api.domain.item.dto.response.ItemResponse;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "찜하기", description = "찜하기 관련 API 입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DibController {
	private final DibService dibService;

	// 유저 권
	@Operation(summary = "특정 상품을 찜하기 합니다.")
	@PostMapping("/dib/{itemId}")
	public ResponseEntity<?> postDib(@PathVariable Long itemId,
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		dibService.postDib(itemId, userDetails);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "특정 상품을 찜하기 취소 합니다.")
	@DeleteMapping("/dib/{itemId}")
	public ResponseEntity<?> deleteDib(@PathVariable Long itemId,
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		dibService.deleteDib(itemId, userDetails);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "내 찜하기 목록을 페이지 단위로 가져옵니다.")
	@GetMapping("/dib/me")
	public ResponseEntity<?> getDibsMe(@RequestParam(required = false, defaultValue = "1") Integer page,
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		List<ItemResponse> responses = dibService.getDibsMe(page, userDetails);
		return ResponseEntity.ok(responses);
	}
}

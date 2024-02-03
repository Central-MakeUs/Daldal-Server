package com.mm.api.domain.search.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.common.swaggerAnnotation.SwaggerResponseSearch;
import com.mm.api.domain.item.dto.response.ItemResponse;
import com.mm.api.domain.search.service.SearchService;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "검색", description = "검색 관련 API 입니다.")
@SwaggerResponseSearch
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SearchController {
	private final SearchService searchService;

	@Operation(summary = "키워드로 검색 합니다.")
	@GetMapping("/search")
	public CommonResponse<List<ItemResponse>> searchKeyword(
		@RequestParam(required = false, defaultValue = "1") Integer page,
		@RequestParam String keyword,
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		List<ItemResponse> itemResponses = searchService.searchKeyword(page, keyword, userDetails);
		return CommonResponse.ok(itemResponses);
	}
}

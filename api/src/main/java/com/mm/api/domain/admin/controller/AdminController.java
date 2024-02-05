package com.mm.api.domain.admin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.domain.admin.service.AdminService;
import com.mm.api.domain.item.dto.response.ItemDetailResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;
	
	@Operation(summary = "url을 입력해 크롤링합니다.", description = "zigzag url만 가능, ex) https://s.zigzag.kr/dV7jnGouAl?af=1")
	@PostMapping("/items/crawl")
	public CommonResponse<ItemDetailResponse> crawlItem(@RequestParam String url) {
		ItemDetailResponse response = adminService.crawlItem(url);
		return CommonResponse.created(response);
	}
}

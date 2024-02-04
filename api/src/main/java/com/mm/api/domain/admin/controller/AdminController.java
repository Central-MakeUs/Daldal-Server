package com.mm.api.domain.admin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.domain.admin.service.AdminService;
import com.mm.api.domain.item.dto.response.ItemDetailResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;

	@PostMapping("/items/crawl")
	public CommonResponse<ItemDetailResponse> crawlItem(@RequestParam String url) {
		ItemDetailResponse response = adminService.crawlItem(url);
		return CommonResponse.created(response);
	}
}

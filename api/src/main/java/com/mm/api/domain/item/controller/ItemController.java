package com.mm.api.domain.item.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;

	@PostMapping("/items")
	public ResponseEntity<?> createItem(@RequestBody ItemCreateRequest request) {
		ItemResponse response = itemService.createItem(request);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/items/{id}")
	public ResponseEntity<?> updateItem(@RequestParam Long id, @RequestBody ItemUpdateRequest request) {
		ItemResponse response = itemService.updateItem(id, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/items/{id}")
	public ResponseEntity<?> updateItem(@RequestParam Long id) {
		itemService.deleteItem(id);
		return ResponseEntity.noContent().build();
	}

	// 관리자 권한

	@GetMapping("/items")
	public ResponseEntity<?> getItems(@RequestParam(required = false, defaultValue = "1") Integer page) {
		List<ItemResponse> responses = itemService.getItems(page);
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/items/{id}")
	public ResponseEntity<?> getItemDetail(@RequestParam Long id) {
		ItemDetailResponse response = itemService.getItemDetail(id);
		return ResponseEntity.ok(response);
	}
}

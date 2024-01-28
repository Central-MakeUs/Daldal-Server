package com.mm.api.domain.dib.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.domain.dib.service.DibService;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DibController {
	private final DibService dibService;

	@PostMapping("/dib/{itemId}")
	public ResponseEntity<?> postDib(@PathVariable Long itemId,
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		dibService.postDib(itemId, userDetails);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/dib/{itemId}")
	public ResponseEntity<?> deleteDib(@PathVariable Long itemId,
		@AuthenticationPrincipal OAuth2UserDetails userDetails) {
		dibService.deleteDib(itemId, userDetails);
		return ResponseEntity.noContent().build();
	}
}

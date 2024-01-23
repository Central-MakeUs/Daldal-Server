package com.mm.api.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.domain.member.dto.request.UpdateMemberAccountRequest;
import com.mm.api.domain.member.dto.response.MemberInfoResponse;
import com.mm.api.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	// 관리자 권한 + 자신만
	
	@PatchMapping("/members/{memberId}/account")
	public ResponseEntity<?> updateMemberAccount(@PathVariable Long memberId,
		@RequestBody UpdateMemberAccountRequest request) {
		memberService.updateMemberAccount(memberId, request);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/members/{memberId}/name")
	public ResponseEntity<?> updateMemberName(@PathVariable Long memberId,
		@RequestParam(value = "name") String name) {
		memberService.updateMemberName(memberId, name);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/members/{memberId}")
	public ResponseEntity<?> getMemberInfo(@PathVariable Long memberId) {
		MemberInfoResponse memberInfo = memberService.getMemberInfo(memberId);
		return ResponseEntity.ok(memberInfo);
	}
}

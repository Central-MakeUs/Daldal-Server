package com.mm.api.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.common.swaggerAnnotation.SwaggerResponseMember;
import com.mm.api.domain.member.dto.request.UpdateMemberAccountRequest;
import com.mm.api.domain.member.dto.response.MemberInfoResponse;
import com.mm.api.domain.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "회원", description = "회원 관련 API 입니다.")
@SwaggerResponseMember
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	// 관리자 권한 + 자신만

	@Operation(summary = "사용자 계좌 정보를 업데이트합니다.", description = """
		depositorName - 입금자명  
		account - 계좌번호  
		accountBank - 은행
		""")
	@PatchMapping("/members/{memberId}/account")
	public CommonResponse<?> updateMemberAccount(@PathVariable Long memberId,
		@RequestBody UpdateMemberAccountRequest request) {
		memberService.updateMemberAccount(memberId, request);
		return CommonResponse.noContent();
	}

	@Operation(summary = "사용자 닉네임을 변경합니다.")
	@PatchMapping("/members/{memberId}/name")
	public CommonResponse<?> updateMemberName(@PathVariable Long memberId,
		@RequestParam(value = "name") String name) {
		memberService.updateMemberName(memberId, name);
		return CommonResponse.noContent();
	}

	@Operation(summary = "특정 사용자의 정보를 가져옵니다.")
	@GetMapping("/members/{memberId}")
	public CommonResponse<MemberInfoResponse> getMemberInfo(@PathVariable Long memberId) {
		MemberInfoResponse memberInfo = memberService.getMemberInfo(memberId);
		return CommonResponse.ok(memberInfo);
	}
}

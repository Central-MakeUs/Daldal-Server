package com.mm.api.domain.member.controller;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.domain.member.dto.request.UpdateMemberAccountRequest;
import com.mm.api.domain.member.dto.response.MemberInfoResponse;
import com.mm.api.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원", description = "회원 관련 API 입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 관리자 권한 + 자신만

    @Operation(summary = "사용자 계좌 정보를 업데이트합니다.")
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
    public CommonResponse<?> getMemberInfo(@PathVariable Long memberId) {
        MemberInfoResponse memberInfo = memberService.getMemberInfo(memberId);
        return CommonResponse.ok(memberInfo);
    }
}

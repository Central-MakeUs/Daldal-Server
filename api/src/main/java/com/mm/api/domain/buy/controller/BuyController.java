package com.mm.api.domain.buy.controller;

import com.mm.api.common.response.CommonResponse;
import com.mm.api.domain.buy.dto.request.RejectBuyRefundStatusRequest;
import com.mm.api.domain.buy.dto.response.BuyResponse;
import com.mm.api.domain.buy.service.BuyService;
import com.mm.coresecurity.oauth.OAuth2UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "구매 인증", description = "구매 인증 관련 API 입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BuyController {
    private final BuyService buyService;

    // 관리자만
    @Operation(summary = "전체 구매 인증을 페이지 단위로 가져옵니다.")
    @GetMapping("/buys")
    public CommonResponse<?> getBuys(@RequestParam(required = false, defaultValue = "1") Integer page) {
        List<BuyResponse> responses = buyService.getBuys(page);
        return CommonResponse.ok(responses);
    }

    @Operation(summary = "구매 인증 상태를 변경합니다.")
    @PatchMapping("/buys/{buyId}/refund-status")
    public CommonResponse<?> updateBuyRefundStatus(@PathVariable Long buyId,
                                                   @RequestParam String refundStatus) {
        BuyResponse response = buyService.updateBuyRefundStatus(buyId, refundStatus);
        return CommonResponse.ok(response);
    }

    @Operation(summary = "구매 인증을 미승인 처리합니다.")
    @PatchMapping("/buys/{buyId}/reject")
    public CommonResponse<?> rejectBuyRefundStatus(@PathVariable Long buyId,
                                                   @RequestBody RejectBuyRefundStatusRequest request) {
        BuyResponse response = buyService.rejectBuyRefundStatus(buyId, request);
        return CommonResponse.ok(response);
    }

    // 관리자 + 회원(자신만)
    @Operation(summary = "구매 인증을 삭제합니다.")
    @DeleteMapping("/buys/{buyId}")
    public CommonResponse<?> deleteBuy(@PathVariable Long buyId) {
        buyService.deleteBuy(buyId);
        return CommonResponse.noContent();
    }

    // 회원만
    @Operation(summary = "구매 인증을 작성합니다.")
    @PostMapping("/buys/{memberId}/{itemId}")
    public CommonResponse<?> postBuy(@PathVariable Long memberId, @PathVariable Long itemId,
                                     @RequestPart(value = "file", required = true) MultipartFile file) {
        BuyResponse buyResponse = buyService.postBuy(memberId, itemId, file);
        return CommonResponse.ok(buyResponse);
    }

    @Operation(summary = "내 구매 인증을 전부 가져옵니다.")
    @GetMapping("/buys/me")
    public CommonResponse<?> getBuysMe(@AuthenticationPrincipal OAuth2UserDetails userDetails) {
        List<BuyResponse> responses = buyService.getBuysMe(userDetails);
        return CommonResponse.ok(responses);
    }
}

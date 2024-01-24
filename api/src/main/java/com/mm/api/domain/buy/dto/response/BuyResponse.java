package com.mm.api.domain.buy.dto.response;

import java.time.LocalDateTime;

import com.mm.coredomain.domain.Buy;
import com.mm.coredomain.domain.RefundStatus;

import lombok.Builder;

@Builder
public record BuyResponse(Long id,
						  String redirectUrl,
						  LocalDateTime uploadTime,
						  Integer refund,
						  RefundStatus refundStatus,
						  String certImageUrl) {
	public static BuyResponse of(Buy buy) {
		return BuyResponse.builder()
			.id(buy.getId())
			.redirectUrl(buy.getRedirectUrl())
			.uploadTime(buy.getUploadTime())
			.refund(buy.getRefund())
			.refundStatus(buy.getRefundStatus())
			.certImageUrl(buy.getCertImageUrl())
			.build();
	}
}
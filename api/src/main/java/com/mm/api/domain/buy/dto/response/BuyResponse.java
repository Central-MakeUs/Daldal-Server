package com.mm.api.domain.buy.dto.response;

import java.time.LocalDateTime;

import com.mm.coredomain.domain.Buy;
import com.mm.coredomain.domain.RefundStatus;

import lombok.Builder;

@Builder
public record BuyResponse(Long id,
						  String redirectUrl,
						  LocalDateTime uploadTime,
						  LocalDateTime approvedTime,
						  Integer pointsBeforeRefund,
						  Integer pointsAfterRefund,
						  Integer purchase,
						  Integer refund,
						  RefundStatus refundStatus,
						  String rejectReason,
						  String certImageUrl) {
	public static BuyResponse of(Buy buy) {
		return BuyResponse.builder()
			.id(buy.getId())
			.redirectUrl(buy.getRedirectUrl())
			.uploadTime(buy.getUploadTime())
			.approvedTime(buy.getApprovedTime())
			.pointsBeforeRefund(buy.getPointsBeforeRefund())
			.pointsAfterRefund(buy.getPointsAfterRefund())
			.purchase(buy.getPurchase())
			.refund(buy.getRefund())
			.refundStatus(buy.getRefundStatus())
			.rejectReason(buy.getRejectReason())
			.certImageUrl(buy.getCertImageUrl())
			.build();
	}
}

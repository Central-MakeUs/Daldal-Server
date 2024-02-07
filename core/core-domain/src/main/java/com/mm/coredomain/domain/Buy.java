package com.mm.coredomain.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Buy extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String redirectUrl;

    private LocalDateTime uploadTime;

    private LocalDateTime approvedTime;

    private Integer refund;

    private RefundStatus refundStatus;

    private String rejectReason;

    @Lob
    private String certImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    public void updateRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    public void setApprovedTimeNow() {
        this.approvedTime = LocalDateTime.now();
    }

    public void approveRefundStatus() {
        this.refundStatus = RefundStatus.COMPLETED;
    }

    public void rejectRefundStatus(String rejectReason) {
        this.refundStatus = RefundStatus.REJECTED;
        this.rejectReason = rejectReason;
    }

    public void approveWithdrawnStatus() {
        this.refundStatus = RefundStatus.WITHDRAWN_COMPLETED;
    }

    public void rejectWithdrawnStatus(String rejectReason) {
        this.refundStatus = RefundStatus.WITHDRAWN_REJECTED;
        this.rejectReason = rejectReason;
    }
}

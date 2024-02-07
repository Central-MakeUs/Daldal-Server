package com.mm.coredomain.domain;

public enum RefundStatus {
    IN_PROGRESS,
    COMPLETED,
    REJECTED,
    WITHDRAWN_IN_PROGRESS,
    WITHDRAWN_COMPLETED,
    WITHDRAWN_REJECTED;

    public static RefundStatus of(String input) {
        try {
            return RefundStatus.valueOf(input.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("존재하지 않는 환급 상태입니다. : " + input);
        }
    }
}

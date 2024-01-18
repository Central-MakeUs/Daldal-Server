package com.mm.coredomain.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String redirectUrl;

    private LocalDateTime orderTime;

    private Integer refund;

    private RefundStatus refundStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
}

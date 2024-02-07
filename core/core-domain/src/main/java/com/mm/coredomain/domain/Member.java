package com.mm.coredomain.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Builder.Default
    private Integer point = 0;

    private String depositorName;

    private String account;

    private String accountBank;

    @Builder.Default
    private MemberStatus memberStatus = MemberStatus.ACTIVE;

    private OAuthProvider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    private Groups groups;

    public void updateMemberAccount(String depositorName, String account, String accountBank) {
        this.depositorName = depositorName;
        this.account = account;
        this.accountBank = accountBank;
    }

    public void updateMemberName(String name) {
        this.name = name;
    }

    public void plusMemberPoint(Integer point) {
        this.point += point;
    }

    public void minusMemberPoint(Integer point) {
        this.point += point;
    }
}

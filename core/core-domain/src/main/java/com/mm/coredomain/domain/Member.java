package com.mm.coredomain.domain;

import com.mm.coredomain.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;

    private String email;

    private String password;

    private Integer point;

    private String accountBank;

    private MemberStatus memberStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;
}

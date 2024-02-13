package com.mm.coredomain.domain;

import jakarta.persistence.*;

@Entity
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @JoinColumn(name = "member_groups_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Groups groups;
}

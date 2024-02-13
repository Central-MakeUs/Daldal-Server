package com.mm.coredomain.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class GroupPermission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_groups_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Groups groups;

    @JoinColumn(name = "permission_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Permission permission;
}

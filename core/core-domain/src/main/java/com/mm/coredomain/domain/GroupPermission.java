package com.mm.coredomain.domain;

import com.mm.coredomain.BaseEntity;
import jakarta.persistence.*;

@Entity
public class GroupPermission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    private Permission permission;
}

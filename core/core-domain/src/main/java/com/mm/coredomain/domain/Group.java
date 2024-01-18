package com.mm.coredomain.domain;

import com.mm.coredomain.BaseEntity;
import com.querydsl.core.types.QList;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Group extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group")
    @JoinColumn(name = "group_id")
    private List<GroupPermission> groupPermissions;
}

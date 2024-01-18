package com.mm.coredomain.domain;

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
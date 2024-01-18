package com.mm.coredomain.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Permission {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}

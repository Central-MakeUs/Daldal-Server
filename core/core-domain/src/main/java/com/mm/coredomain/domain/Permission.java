package com.mm.coredomain.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Permission extends BaseEntity {
	@Id
	@GeneratedValue
	private Long id;

	private String name;
}

package com.mm.coredomain.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class GroupPermission extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "groups_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Groups groups;

	@JoinColumn(name = "permission_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Permission permission;
}

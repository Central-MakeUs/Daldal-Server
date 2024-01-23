package com.mm.coredomain.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	private Integer point;

	private String account;

	private String accountBank;

	private MemberStatus memberStatus;

	private OAuthProvider provider;

	@ManyToOne(fetch = FetchType.LAZY)
	private Groups groups;

	public void updateMemberAccount(String account, String accountBank) {
		this.account = account;
		this.accountBank = accountBank;
	}

	public void updateMemberName(String name) {
		this.name = name;
	}
}

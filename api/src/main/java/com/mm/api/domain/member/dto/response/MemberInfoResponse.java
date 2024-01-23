package com.mm.api.domain.member.dto.response;

import com.mm.coredomain.domain.Member;
import com.mm.coredomain.domain.MemberStatus;
import com.mm.coredomain.domain.OAuthProvider;

import lombok.Builder;

@Builder
public record MemberInfoResponse(Long id,
								 String name,
								 String email,
								 Integer point,
								 String account,
								 String accountBank,
								 MemberStatus memberStatus,
								 OAuthProvider provider
) {
	public static MemberInfoResponse of(Member member) {
		return MemberInfoResponse.builder()
			.id(member.getId())
			.name(member.getName())
			.email(member.getEmail())
			.point(member.getPoint())
			.account(member.getAccount())
			.accountBank(member.getAccountBank())
			.memberStatus(member.getMemberStatus())
			.provider(member.getProvider())
			.build();
	}
}

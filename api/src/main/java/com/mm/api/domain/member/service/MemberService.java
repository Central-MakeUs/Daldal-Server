package com.mm.api.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.api.domain.member.dto.request.UpdateMemberAccountRequest;
import com.mm.api.domain.member.dto.response.MemberInfoResponse;
import com.mm.api.exception.CustomException;
import com.mm.api.exception.ErrorCode;
import com.mm.coredomain.domain.Member;
import com.mm.coredomain.repository.MemberRepository;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public void updateMemberAccount(OAuth2UserDetails userDetails, UpdateMemberAccountRequest request) {
		Member member = getMember(userDetails.getId());
		member.updateMemberAccount(request.depositorName(), request.account(), request.accountBank());
	}

	public void updateMemberName(OAuth2UserDetails userDetails, String name) {
		Member member = getMember(userDetails.getId());
		member.updateMemberName(name);
	}

	public MemberInfoResponse getMemberInfo(Long memberId) {
		Member member = getMember(memberId);
		return MemberInfoResponse.of(member);
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
	}
}

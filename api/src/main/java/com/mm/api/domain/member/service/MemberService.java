package com.mm.api.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.api.domain.member.dto.request.UpdateMemberAccountRequest;
import com.mm.api.domain.member.dto.response.MemberInfoResponse;
import com.mm.api.exception.CustomException;
import com.mm.api.exception.ErrorCode;
import com.mm.coredomain.domain.Member;
import com.mm.coredomain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	// 관리자 + 회원 권한(자신만)
	public void updateMemberAccount(Long memberId, UpdateMemberAccountRequest request) {
		Member member = getMember(memberId);
		member.updateMemberAccount(request.account(), request.accountBank());
	}

	public void updateMemberName(Long memberId, String name) {
		Member member = getMember(memberId);
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

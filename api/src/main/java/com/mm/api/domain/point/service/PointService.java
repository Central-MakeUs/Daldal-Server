package com.mm.api.domain.point.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.api.domain.buy.dto.response.BuyResponse;
import com.mm.api.exception.CustomException;
import com.mm.api.exception.ErrorCode;
import com.mm.coredomain.domain.Buy;
import com.mm.coredomain.domain.Member;
import com.mm.coredomain.domain.RefundStatus;
import com.mm.coredomain.repository.BuyRepository;
import com.mm.coredomain.repository.MemberRepository;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PointService {
	private final MemberRepository memberRepository;
	private final BuyRepository buyRepository;

	public Integer getMyPoint(OAuth2UserDetails userDetails) {
		Long memberId = userDetails.getId();
		return getMember(memberId).getPoint();
	}

	public List<BuyResponse> getCumulativeHistory(OAuth2UserDetails userDetails) {
		Long memberId = userDetails.getId();
		Member member = getMember(memberId);

		return buyRepository.findAllByMember(member)
			.stream()
			.filter(this::isRefundCumulative)
			.map(BuyResponse::of)
			.toList();
	}

	public List<BuyResponse> getExpectedHistory(OAuth2UserDetails userDetails) {
		Long memberId = userDetails.getId();
		Member member = getMember(memberId);

		return buyRepository.findAllByMember(member)
			.stream()
			.filter(this::isRefundExpected)
			.map(BuyResponse::of)
			.toList();
	}

	private boolean isRefundCumulative(Buy buy) {
		return buy.getRefundStatus().equals(RefundStatus.COMPLETED) ||
			buy.getRefundStatus().equals(RefundStatus.REJECTED);
	}

	private boolean isRefundExpected(Buy buy) {
		return buy.getRefundStatus().equals(RefundStatus.COMPLETED) ||
			buy.getRefundStatus().equals(RefundStatus.REJECTED);
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
	}
}

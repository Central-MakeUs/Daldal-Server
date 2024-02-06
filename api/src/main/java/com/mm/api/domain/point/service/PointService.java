package com.mm.api.domain.point.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.api.domain.buy.dto.response.BuyResponse;
import com.mm.api.domain.point.dto.response.PointsResponse;
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

	public PointsResponse getCumulativeHistory(OAuth2UserDetails userDetails) {
		Long memberId = userDetails.getId();
		Member member = getMember(memberId);

		List<Buy> buys = buyRepository.findAllByMember(member);
		List<BuyResponse> buyResponses = buys
			.stream()
			.filter(this::isRefundCumulative)
			.map(BuyResponse::of)
			.toList();
		Integer totalPoint = buys.stream()
			.filter(this::isRefundCumulativeTotalPoint)
			.map(Buy::getRefund)
			.reduce(0, Integer::sum);

		return new PointsResponse(totalPoint, buyResponses);
	}

	public PointsResponse getExpectedHistory(OAuth2UserDetails userDetails) {
		Long memberId = userDetails.getId();
		Member member = getMember(memberId);

		List<Buy> buys = buyRepository.findAllByMember(member);
		List<BuyResponse> buyResponses = buyRepository.findAllByMember(member)
			.stream()
			.filter(this::isRefundExpected)
			.map(BuyResponse::of)
			.toList();
		Integer totalPoint = buys.stream()
			.filter(this::isRefundExpectedTotalPoint)
			.map(Buy::getRefund)
			.reduce(0, Integer::sum);

		return new PointsResponse(totalPoint, buyResponses);
	}

	private boolean isRefundCumulativeTotalPoint(Buy buy) {
		return buy.getRefundStatus().equals(RefundStatus.COMPLETED);
	}

	private boolean isRefundExpectedTotalPoint(Buy buy) {
		return buy.getRefundStatus().equals(RefundStatus.IN_PROGRESS);
	}

	private boolean isRefundCumulative(Buy buy) {
		return buy.getRefundStatus().equals(RefundStatus.COMPLETED) ||
			buy.getRefundStatus().equals(RefundStatus.REJECTED);
	}

	private boolean isRefundExpected(Buy buy) {
		return buy.getRefundStatus().equals(RefundStatus.IN_PROGRESS);
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
	}
}

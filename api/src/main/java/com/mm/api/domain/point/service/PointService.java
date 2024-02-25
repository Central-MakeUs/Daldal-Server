package com.mm.api.domain.point.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

	@Transactional(readOnly = true)
	public Integer getMyPoint(OAuth2UserDetails userDetails) {
		Long memberId = userDetails.getId();
		return getMember(memberId).getPoint();
	}

	// TODO 쿼리로 가져오게 개선
	@Transactional(readOnly = true)
	public PointsResponse getCumulativeHistory(OAuth2UserDetails userDetails) {
		Long memberId = userDetails.getId();
		Member member = getMember(memberId);

		List<Buy> buys = buyRepository.findAllByMember(member);
		List<BuyResponse> buyResponses = buys
			.stream()
			.filter(this::isRefundCumulative)
			.map(buy -> BuyResponse.of(buy, buy.getMember()))
			.toList();
		List<BuyResponse> reveredBuyResponses = new ArrayList<>(buyResponses);
		Collections.reverse(reveredBuyResponses);

		Integer totalPoint = buys.stream()
			.filter(this::isRefundCumulativeTotalPoint)
			.map(buy -> {
				if (buy.getRefund() == null) {
					return 0;
				}
				return buy.getRefund();
			})
			.reduce(0, Integer::sum);

		return new PointsResponse(totalPoint, reveredBuyResponses);
	}

	@Transactional(readOnly = true)
	public PointsResponse getExpectedHistory(OAuth2UserDetails userDetails) {
		Long memberId = userDetails.getId();
		Member member = getMember(memberId);

		List<Buy> buys = buyRepository.findAllByMember(member);
		List<BuyResponse> buyResponses = buyRepository.findAllByMember(member)
			.stream()
			.filter(this::isRefundExpected)
			.map(buy -> BuyResponse.of(buy, buy.getMember()))
			.toList();
		List<BuyResponse> reveredBuyResponses = new ArrayList<>(buyResponses);
		Collections.reverse(reveredBuyResponses);

		Integer totalPoint = buys.stream()
			.filter(this::isRefundExpectedTotalPoint)
			.map(buy -> {
				if (buy.getRefund() == null) {
					return 0;
				}
				return buy.getRefund();
			})
			.reduce(0, Integer::sum);

		return new PointsResponse(totalPoint, reveredBuyResponses);
	}

	public BuyResponse postPointsWithdraw(OAuth2UserDetails userDetails, Integer refund) {
		Member member = memberRepository.findByIdWithPessimisticLock(userDetails.getId());

		isPointsEnoughForRefund(refund, member);

		Buy buy = Buy.builder()
			.member(member)
			.refund(refund)
			.uploadTime(LocalDateTime.now())
			.refundStatus(RefundStatus.WITHDRAWN_IN_PROGRESS)
			.build();

		Buy savedBuy = buyRepository.save(buy);
		member.minusMemberPoint(refund);

		return BuyResponse.of(savedBuy, savedBuy.getMember());
	}

	private void isPointsEnoughForRefund(Integer refund, Member member) {
		if (refund < 1000) {
			throw new CustomException(ErrorCode.POINTS_WITHDRAW_MIN);
		}
		if (refund > 5000) {
			throw new CustomException(ErrorCode.POINTS_WITHDRAW_MAX);
		}
		if (refund > member.getPoint()) {
			throw new CustomException(ErrorCode.POINTS_WITHDRAW_NOT_ENOUGH);
		}
	}

	private boolean isRefundCumulativeTotalPoint(Buy buy) {
		return buy.getRefundStatus().equals(RefundStatus.COMPLETED);
	}

	private boolean isRefundExpectedTotalPoint(Buy buy) {
		return buy.getRefundStatus().equals(RefundStatus.IN_PROGRESS);
	}

	private boolean isRefundCumulative(Buy buy) {
		return buy.getRefundStatus().equals(RefundStatus.COMPLETED) ||
			buy.getRefundStatus().equals(RefundStatus.REJECTED) ||
			buy.getRefundStatus().equals(RefundStatus.WITHDRAWN_COMPLETED) ||
			buy.getRefundStatus().equals(RefundStatus.WITHDRAWN_REJECTED);
	}

	private boolean isRefundExpected(Buy buy) {
		return buy.getRefundStatus().equals(RefundStatus.IN_PROGRESS) ||
			buy.getRefundStatus().equals(RefundStatus.WITHDRAWN_IN_PROGRESS);
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
	}
}

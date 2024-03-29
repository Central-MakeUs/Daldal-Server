package com.mm.api.domain.buy.service;

import static com.mm.api.exception.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mm.api.domain.buy.dto.response.BuyMeListResponse;
import com.mm.api.domain.buy.dto.response.BuyResponse;
import com.mm.api.exception.CustomException;
import com.mm.api.exception.ErrorCode;
import com.mm.coredomain.domain.Buy;
import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.Member;
import com.mm.coredomain.domain.RefundStatus;
import com.mm.coredomain.repository.BuyRepository;
import com.mm.coredomain.repository.ItemRepository;
import com.mm.coredomain.repository.MemberRepository;
import com.mm.coreinfraqdsl.repository.BuyCustomRepository;
import com.mm.coreinfras3.util.S3Service;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BuyService {
	private final BuyRepository buyRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	private final S3Service s3Service;
	private final BuyCustomRepository buyCustomRepository;

	public BuyResponse postBuy(OAuth2UserDetails userDetails, MultipartFile file) {
		Member member = getMember(userDetails.getId());
		// Item item = getItem(itemId);

		String url = s3Service.uploadFileToS3(file, member.getId(), 0L);

		Buy buy = Buy.builder()
			.member(member)
			.item(null)
			// .redirectUrl(item.getRedirectUrl())
			// .purchase(item.getPrice())
			// .refund(item.getRefund())
			.refundStatus(RefundStatus.IN_PROGRESS)
			.uploadTime(LocalDateTime.now())
			.certImageUrl(url)
			.build();

		return BuyResponse.of(buyRepository.save(buy), buy.getMember());
	}

	public BuyResponse updateBuyRefundStatus(Long buyId, String refundStatus) {
		RefundStatus convertedRefundStatus = RefundStatus.of(refundStatus);
		Buy buy = getBuy(buyId);
		buy.updateRefundStatus(convertedRefundStatus);

		return BuyResponse.of(buy, buy.getMember());
	}

	public void deleteBuy(Long buyId) {
		Buy buy = getBuy(buyId);
		buyRepository.delete(buy);
	}

	public BuyMeListResponse getBuysMe(Integer page, OAuth2UserDetails userDetails) {
		Member member = getMember(userDetails.getId());

		List<BuyResponse> buyResponses = buyCustomRepository.getBuysMeByMember(page, member)
			.stream()
			.map(buy -> BuyResponse.of(buy, buy.getMember()))
			.toList();

		Long pageNum = buyCustomRepository.getBuysMePageNum(member);
		Boolean isLastPage = pageNum.equals(page.longValue());

		return new BuyMeListResponse(isLastPage, buyResponses);
	}

	public BuyResponse getBuyResponse(Long buyId) {
		Buy buy = getBuy(buyId);
		return BuyResponse.of(buy, buy.getMember());
	}

	private Buy getBuy(Long buyId) {
		return buyRepository.findById(buyId)
			.orElseThrow(() -> new CustomException(BUY_NOT_FOUND));
	}

	private Item getItem(Long id) {
		return itemRepository.findById(id)
			.orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
	}
}

package com.mm.api.domain.buy.service;

import static com.mm.api.exception.ErrorCode.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mm.api.exception.CustomException;
import com.mm.api.exception.ErrorCode;
import com.mm.coredomain.domain.Buy;
import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.Member;
import com.mm.coredomain.domain.RefundStatus;
import com.mm.coredomain.repository.BuyRepository;
import com.mm.coredomain.repository.ItemRepository;
import com.mm.coredomain.repository.MemberRepository;
import com.mm.coreinfras3.util.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyService {
	private final BuyRepository buyRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	private final S3Service s3Service;

	public void postBuy(Long memberId, Long itemId, MultipartFile file) {
		Member member = getMember(memberId);
		Item item = getItem(itemId);

		String url = s3Service.uploadFileToS3(file, memberId, itemId);

		Buy buy = Buy.builder()
			.member(member)
			.item(item)
			.redirectUrl(item.getRedirectUrl())
			.refund(item.getRefund())
			.refundStatus(RefundStatus.IN_PROGRESS)
			.uploadTime(LocalDateTime.now())
			.certImageUrl(url)
			.build();

		buyRepository.save(buy);
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

package com.mm.api.domain.dib.service;

import static com.mm.api.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mm.api.exception.CustomException;
import com.mm.api.exception.ErrorCode;
import com.mm.coredomain.domain.Dib;
import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.Member;
import com.mm.coredomain.repository.DibRepository;
import com.mm.coredomain.repository.ItemRepository;
import com.mm.coredomain.repository.MemberRepository;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DibService {
	private final DibRepository dibRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	public List<Boolean> getDib(Member member, List<Item> items) {
		return items.stream()
			.map(item -> isDibExist(member, item))
			.toList();
	}

	public void postDib(Long itemId, OAuth2UserDetails userDetails) {
		Member member = getMember(userDetails.getId());
		Item item = getItem(itemId);

		if (isDibExist(member, item)) {
			throw new CustomException(DIB_ALREADY_EXIST);
		}

		Dib dib = Dib.builder()
			.member(member)
			.item(item)
			.build();

		dibRepository.save(dib);
	}

	public void deleteDib(Long itemId, OAuth2UserDetails userDetails) {
		Member member = getMember(userDetails.getId());
		Item item = getItem(itemId);

		Dib dib = dibRepository.findByMemberAndItem(member, item)
			.orElseThrow(() -> new CustomException(DIB_NOT_FOUND));
		dibRepository.delete(dib);
	}

	private boolean isDibExist(Member member, Item item) {
		return dibRepository.findByMemberAndItem(member, item).isPresent();
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

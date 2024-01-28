package com.mm.api.domain.dib.service;

import static com.mm.api.exception.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.api.domain.item.dto.response.ItemResponse;
import com.mm.api.exception.CustomException;
import com.mm.api.exception.ErrorCode;
import com.mm.coredomain.domain.Dib;
import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.Member;
import com.mm.coredomain.repository.DibRepository;
import com.mm.coredomain.repository.ItemRepository;
import com.mm.coredomain.repository.MemberRepository;
import com.mm.coreinfraqdsl.repository.DibCustomRepository;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DibService {
	private final DibRepository dibRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	private final DibCustomRepository dibCustomRepository;

	@Transactional(readOnly = true)
	public List<Boolean> getDib(List<Item> items, OAuth2UserDetails userDetails) {
		if (userDetails == null) {
			return items.stream()
				.map(item -> false)
				.toList();
		}

		Long memberId = userDetails.getId();
		Member member = getMember(memberId);
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

	public List<ItemResponse> getDibsMe(Integer page, OAuth2UserDetails userDetails) {
		Member member = getMember(userDetails.getId());
		List<Dib> dibs = dibCustomRepository.getDibsByPage(page, member);
		return dibs.stream()
			.map(dib ->
				ItemResponse.of(dib.getItem(), true))
			.toList();
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

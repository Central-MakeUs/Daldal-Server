package com.mm.api.domain.search.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.api.domain.dib.service.DibService;
import com.mm.api.domain.item.dto.response.ItemResponse;
import com.mm.api.domain.search.dto.SearchResponse;
import com.mm.coredomain.domain.Item;
import com.mm.coreinfraqdsl.repository.SearchCustomRepository;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {
	private final SearchCustomRepository searchCustomRepository;
	private final DibService dibService;

	@Transactional(readOnly = true)
	public SearchResponse searchKeyword(Integer page, String keyword, OAuth2UserDetails userDetails) {
		List<Item> items = searchCustomRepository.searchItems(page, keyword);
		List<Boolean> dibs = dibService.getDib(items, userDetails);

		List<ItemResponse> itemResponses = IntStream.range(0, items.size())
			.mapToObj(i ->
				ItemResponse.of(items.get(i), dibs.get(i)))
			.toList();

		Long pageNum = searchCustomRepository.getPageNum(keyword);
		Boolean isLastPage = pageNum.equals(page.longValue());

		Long count = searchCustomRepository.getSearchResultNumber(keyword);

		return new SearchResponse(isLastPage, count, itemResponses);
	}
}

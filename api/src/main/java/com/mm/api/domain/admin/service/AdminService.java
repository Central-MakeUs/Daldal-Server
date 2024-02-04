package com.mm.api.domain.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.api.domain.item.dto.response.ItemDetailResponse;
import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemImage;
import com.mm.coredomain.domain.ItemVideo;
import com.mm.coredomain.repository.ItemRepository;
import com.mm.coreinfrafeign.service.CrawlerService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
	private ItemRepository itemRepository;

	private CrawlerService crawlerService;

	public ItemDetailResponse crawlItem(String url) {
		Item savedItem = itemRepository.save(crawlerService.getZigZagItemByCrawler(url));
		return getItemDetailResponseByItem(savedItem);
	}

	private ItemDetailResponse getItemDetailResponseByItem(Item savedItem) {
		List<String> images = savedItem.getItemImages().stream()
			.map(ItemImage::getUrl)
			.toList();
		List<String> videos = savedItem.getItemVideos().stream()
			.map(ItemVideo::getUrl)
			.toList();
		return ItemDetailResponse.of(savedItem, images, videos, false);
	}
}

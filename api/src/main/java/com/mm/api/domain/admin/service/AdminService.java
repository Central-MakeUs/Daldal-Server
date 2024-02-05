package com.mm.api.domain.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.api.domain.item.dto.response.ItemDetailResponse;
import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemImage;
import com.mm.coredomain.domain.ItemVideo;
import com.mm.coredomain.repository.ItemRepository;
import com.mm.coreinfrafeign.crawler.service.CrawlerService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
	private final ItemRepository itemRepository;
	private final CrawlerService crawlerService;

	public ItemDetailResponse crawlItem(String url) {
		Item item = crawlerService.getZigZagItemByCrawler(url);
		Item savedItem = itemRepository.save(item);
		return getItemDetailResponseByItem(savedItem);
	}

	private ItemDetailResponse getItemDetailResponseByItem(Item savedItem) {
		List<String> images = null;
		if (savedItem.getItemImages() != null) {
			images = savedItem.getItemImages().stream()
				.map(ItemImage::getUrl)
				.toList();
		}
		List<String> videos = null;
		if (savedItem.getItemVideos() != null) {
			videos = savedItem.getItemVideos().stream()
				.map(ItemVideo::getUrl)
				.toList();
		}
		return ItemDetailResponse.of(savedItem, images, videos, false);
	}
}

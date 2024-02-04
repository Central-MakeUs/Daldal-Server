package com.mm.coreinfrafeign.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;
import com.mm.coredomain.domain.ItemImage;
import com.mm.coreinfrafeign.client.ZigZagCrawlerClient;
import com.mm.coreinfrafeign.dto.requset.ZigZagCrawlerRequest;
import com.mm.coreinfrafeign.dto.response.ZigZagCrawlerResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CrawlerService {
	private final ZigZagCrawlerClient zigZagCrawlerClient;

	public Item getZigZagItemByCrawler(String redirectUrl) {
		ZigZagCrawlerResponse response = zigZagCrawlerClient.call(new ZigZagCrawlerRequest(redirectUrl));

		List<ItemImage> itemImages = response.productImageList()
			.stream()
			.map(image -> ItemImage.builder()
				.url(image.url())
				.build())
			.toList();

		ItemCategoryType categoryType = getCategoryType(response);

		return Item.builder()
			.price(response.finalPrice())
			.title(response.name())
			.redirectUrl(response.pageUrl())
			.categoryType(categoryType)
			.itemImages(itemImages)
			.refund(getRefundPrice(response.finalPrice(), categoryType.getRefundPercent()))
			.thumbnailUrl(response.thumbnailUrl())
			.build();
	}

	private static ItemCategoryType getCategoryType(ZigZagCrawlerResponse response) {
		return ItemCategoryType.fromValue(response.managedCategoryList().get(0).value());
	}

	private Integer getRefundPrice(Integer price, Integer percent) {
		return price * percent / 100;
	}
}

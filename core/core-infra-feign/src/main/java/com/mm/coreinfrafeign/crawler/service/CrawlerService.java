package com.mm.coreinfrafeign.crawler.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;
import com.mm.coredomain.domain.ItemImage;
import com.mm.coreinfrafeign.crawler.client.ZigZagCrawlerClient;
import com.mm.coreinfrafeign.crawler.dto.requset.ZigZagCrawlerRequest;
import com.mm.coreinfrafeign.crawler.dto.response.ZigZagCrawlerResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CrawlerService {
	private final ZigZagCrawlerClient zigZagCrawlerClient;

	public Item getZigZagItemByCrawler(String redirectUrl) {
		ZigZagCrawlerResponse response = zigZagCrawlerClient.call(new ZigZagCrawlerRequest(redirectUrl));

		ItemCategoryType categoryType = getCategoryType(response);

		Item item = Item.builder()
			.price(response.finalPrice())
			.title(response.name())
			.redirectUrl(redirectUrl)
			.categoryType(categoryType)
			.refund(getRefundPrice(response.finalPrice(), categoryType.getRefundPercent()))
			.thumbnailUrl(response.thumbnailUrl())
			.build();

		List<ItemImage> itemImages = response.productImageList()
			.stream()
			.map(image -> ItemImage.builder()
				.item(item)
				.url(image.url())
				.build())
			.toList();

		item.setItemImages(itemImages);
		return item;
	}

	private static ItemCategoryType getCategoryType(ZigZagCrawlerResponse response) {
		String itemCategory = response.managedCategoryList().get(0).value();
		if (itemCategory.equals("패션의류")) {
			itemCategory = response.managedCategoryList().get(2).value();
			return ItemCategoryType.fromValueForClothes(itemCategory);
		}

		return ItemCategoryType.fromValue(itemCategory);
	}

	private Integer getRefundPrice(Integer price, Integer percent) {
		return price * percent / 100;
	}
}

package com.mm.api.domain.item.dto.request;

import java.util.List;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;

public record ItemCreateRequest(String title,
								String redirectUrl,
								String categoryType,
								Integer price,
								Integer refund,
								Double rating,
								String thumbnailUrl,
								List<String> imageUrls,
								List<String> videoUrls
) {
	public Item toEntity() {
		return Item.builder()
			.title(title)
			.redirectUrl(redirectUrl)
			.categoryType(ItemCategoryType.of(categoryType))
			.price(price)
			.refund(refund)
			.rating(rating)
			.thumbnailUrl(thumbnailUrl)
			.build();
	}
}

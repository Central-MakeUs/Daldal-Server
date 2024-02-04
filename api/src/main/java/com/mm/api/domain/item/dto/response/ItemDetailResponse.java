package com.mm.api.domain.item.dto.response;

import java.util.List;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;

public record ItemDetailResponse(Long id,
								 String title,
								 String redirectUrl,
								 ItemCategoryType categoryType,
								 Integer price,
								 Integer refund,
								 Double rating,
								 String thumbnailUrl,
								 List<String> imageUrls,
								 List<String> videoUrls,
								 Boolean isDib) {
	public static ItemDetailResponse of(Item item, List<String> imageUrls, List<String> videoUrls, Boolean isDib) {
		return new ItemDetailResponse(
			item.getId(),
			item.getTitle(),
			item.getRedirectUrl(),
			item.getCategoryType(),
			item.getPrice(),
			item.getRefund(),
			item.getRating(),
			item.getThumbnailUrl(),
			imageUrls,
			videoUrls,
			isDib
		);
	}
}

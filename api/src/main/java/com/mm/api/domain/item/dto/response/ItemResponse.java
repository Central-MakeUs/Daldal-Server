package com.mm.api.domain.item.dto.response;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;

public record ItemResponse(Long id,
						   String title,
						   String redirectUrl,
						   ItemCategoryType categoryType,
						   Integer price,
						   Integer refund,
						   String thumbnailUrl,
						   Boolean isDib) {
	public static ItemResponse of(Item item, Boolean isDib) {
		return new ItemResponse(
			item.getId(),
			item.getTitle(),
			item.getRedirectUrl(),
			item.getCategoryType(),
			item.getPrice(),
			item.getRefund(),
			item.getThumbnailUrl(),
			isDib
		);
	}
}

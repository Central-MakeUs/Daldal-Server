package com.mm.api.domain.item.dto.response;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;

public record ItemResponse(Long id,
						   String detail,
						   String redirectUrl,
						   ItemCategoryType categoryType,
						   Integer price,
						   Integer refund,
						   Double rating,
						   String thumbnailUrl,
						   Boolean isDib) {
	public static ItemResponse of(Item item, Boolean isDib) {
		return new ItemResponse(
			item.getId(),
			item.getDetail(),
			item.getRedirectUrl(),
			item.getCategoryType(),
			item.getPrice(),
			item.getRefund(),
			item.getRating(),
			item.getThumbnailUrl(),
			isDib
		);
	}
}

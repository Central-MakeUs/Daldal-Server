package com.mm.api.domain.admin.dto.response;

import java.util.List;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;

public record AdminItemListResponse(Long lastPageNum, List<AdminItemResponse> adminItemResponses) {
	public static record AdminItemResponse(Long id,
										   String title,
										   String redirectUrl,
										   ItemCategoryType categoryType,
										   Integer price,
										   Integer refund,
										   String thumbnailUrl,
										   List<String> imageUrls,
										   List<String> videoUrls) {
		public static AdminItemResponse of(Item item, List<String> imageUrls, List<String> videoUrls) {
			return new AdminItemResponse(
				item.getId(),
				item.getTitle(),
				item.getRedirectUrl(),
				item.getCategoryType(),
				item.getPrice(),
				item.getRefund(),
				item.getThumbnailUrl(),
				imageUrls,
				videoUrls
			);
		}
	}
}

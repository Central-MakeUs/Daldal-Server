package com.mm.coredomain.domain;

import lombok.Builder;

@Builder
public record ItemUpdate(String title,
						 String detail,
						 String redirectUrl,
						 ItemCategoryType categoryType,
						 Integer price,
						 Integer refund,
						 Double rating,
						 String thumbnailUrl) {
}

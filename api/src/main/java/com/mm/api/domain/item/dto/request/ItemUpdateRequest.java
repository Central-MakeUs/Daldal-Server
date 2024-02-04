package com.mm.api.domain.item.dto.request;

import java.util.List;

public record ItemUpdateRequest(String title,
								String redirectUrl,
								String categoryType,
								Integer price,
								Integer refund,
								Double rating,
								String thumbnailUrl,
								List<String> imageUrls,
								List<String> videoUrls) {
}

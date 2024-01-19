package com.mm.api.Item.dto.request;

import java.util.List;

public record ItemUpdateRequest(String detail,
                                String redirectUrl,
                                String categoryType,
                                Integer price,
                                Integer refund,
                                Double rating,
                                String thumbnailUrl,
                                List<String> imageUrls,
                                List<String> videoUrls) {
}

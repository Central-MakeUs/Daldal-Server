package com.mm.api.Item.dto.request;

public record ItemUpdateRequest(String detail,
                                String redirectUrl,
                                String categoryType,
                                Integer price,
                                Integer refund,
                                Double rating,
                                String thumbnailUrl) {
}

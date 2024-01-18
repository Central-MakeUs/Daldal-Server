package com.mm.coredomain.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public record ItemUpdate(String detail,
                         String redirectUrl,
                         ItemCategoryType categoryType,
                         Integer price,
                         Integer refund,
                         Double rating,
                         String thumbnailUrl) {
}

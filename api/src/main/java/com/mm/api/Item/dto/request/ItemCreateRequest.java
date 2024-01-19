package com.mm.api.Item.dto.request;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;

import java.util.List;

public record ItemCreateRequest(String detail,
                                String redirectUrl,
                                String categoryType,
                                Integer price,
                                Integer refund,
                                Double rating,
                                String thumbnailUrl,
                                List<String> imageUrls,
                                List<String> videoUrls
                                ) {
    public Item toEntity(){
        return Item.builder()
                .detail(detail)
                .redirectUrl(redirectUrl)
                .categoryType(ItemCategoryType.of(categoryType))
                .price(price)
                .refund(refund)
                .rating(rating)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}

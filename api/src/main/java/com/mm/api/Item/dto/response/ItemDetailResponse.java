package com.mm.api.Item.dto.response;

import com.mm.coredomain.domain.Item;
import com.mm.coredomain.domain.ItemCategoryType;

import java.util.List;

public record ItemDetailResponse(Long id,
                                 String detail,
                                 String redirectUrl,
                                 ItemCategoryType categoryType,
                                 Integer price,
                                 Integer refund,
                                 Double rating,
                                 String thumbnailUrl,
                                 List<String> imageUrls,
                                 List<String> videoUrls) {
    public static ItemDetailResponse of(Item item, List<String> imageUrls, List<String> videoUrls){
        return new ItemDetailResponse(
                item.getId(),
                item.getDetail(),
                item.getRedirectUrl(),
                item.getCategoryType(),
                item.getPrice(),
                item.getRefund(),
                item.getRating(),
                item.getThumbnailUrl(),
                imageUrls,
                videoUrls
        );
    }
}

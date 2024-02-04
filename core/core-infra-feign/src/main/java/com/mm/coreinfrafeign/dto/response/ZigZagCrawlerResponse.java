package com.mm.coreinfrafeign.dto.response;

import java.util.List;

public record ZigZagCrawlerResponse(
	String type,
	String pageUrl,
	String siteName,
	String price,
	String brand,
	String thumbnailUrl,
	String name,
	String categoryKey,
	List<Category> categoryList,
	List<ProductImage> productImageList,
	List<Category> managedCategoryList,
	int originalPrice,
	int finalPrice
) {
	public static record Category(int categoryId, String value) {
	}

	public static record ProductImage(
		String id,
		String url,
		String originUrl,
		String pdpThumbnailUrl,
		String pdpStaticImageUrl,
		String imageType,
		int originWidth,
		int originHeight
	) {
	}
}

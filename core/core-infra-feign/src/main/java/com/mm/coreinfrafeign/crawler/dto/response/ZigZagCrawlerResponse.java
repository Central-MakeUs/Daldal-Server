package com.mm.coreinfrafeign.crawler.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record ZigZagCrawlerResponse(
	@JsonProperty("type") String type,
	@JsonProperty("page_url") String pageUrl,
	@JsonProperty("site_name") String siteName,
	@JsonProperty("price") String price,
	@JsonProperty("brand") String brand,
	@JsonProperty("thumbnail_url") String thumbnailUrl,
	@JsonProperty("name") String name,
	@JsonProperty("category_key") String categoryKey,
	@JsonProperty("category_list") List<Category> categoryList,
	@JsonProperty("product_image_list") List<ProductImage> productImageList,
	@JsonProperty("managed_category_list") List<Category> managedCategoryList,
	@JsonProperty("original_price") int originalPrice,
	@JsonProperty("final_price") int finalPrice
) {
	public static record Category(@JsonProperty("category_id") int categoryId, @JsonProperty("value") String value) {
	}

	public static record ProductImage(
		@JsonProperty("id") String id,
		@JsonProperty("url") String url,
		@JsonProperty("origin_url") String originUrl,
		@JsonProperty("pdp_thumbnail_url") String pdpThumbnailUrl,
		@JsonProperty("pdp_static_image_url") String pdpStaticImageUrl,
		@JsonProperty("image_type") String imageType,
		@JsonProperty("origin_width") int originWidth,
		@JsonProperty("origin_height") int originHeight
	) {
	}
}

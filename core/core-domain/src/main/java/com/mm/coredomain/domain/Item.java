package com.mm.coredomain.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE item SET deleted = true WHERE id = ?")
public class Item extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Lob
	private String redirectUrl;

	@Enumerated(value = EnumType.STRING)
	private ItemCategoryType categoryType;

	private Integer price;

	private Integer refund;

	private Double rating;

	@Lob
	private String thumbnailUrl;

	@Builder.Default
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemImage> itemImages = new ArrayList<ItemImage>();

	@Builder.Default
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemVideo> itemVideos = new ArrayList<ItemVideo>();

	public void setItemImages(List<ItemImage> itemImages) {
		this.itemImages.clear();
		itemImages.forEach(itemImage -> this.itemImages.add(itemImage));
	}

	public void setItemVideos(List<ItemVideo> itemVideos) {
		this.itemVideos.clear();
		itemVideos.forEach(itemVideo -> this.itemVideos.add(itemVideo));
	}

	public void updateItem(ItemUpdate itemUpdate) {
		this.title = itemUpdate.title();
		this.redirectUrl = itemUpdate.redirectUrl();
		this.categoryType = itemUpdate.categoryType();
		this.price = itemUpdate.price();
		this.refund = itemUpdate.refund();
		this.rating = itemUpdate.rating();
		this.thumbnailUrl = itemUpdate.thumbnailUrl();
	}
}

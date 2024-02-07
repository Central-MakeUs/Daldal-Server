package com.mm.coredomain.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;

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
    private Boolean isSuggested = false;

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

    public void setItemSuggested() {
        this.isSuggested = true;
    }

    public void setItemNotSuggested() {
        this.isSuggested = false;
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

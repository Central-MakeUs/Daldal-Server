package com.mm.coredomain.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

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

    @Lob
    private String detail;

    @Lob
    private String redirectUrl;

    @Enumerated(value = EnumType.STRING)
    private ItemCategoryType categoryType;

    private Integer price;

    private Integer refund;

    private Double rating;

    @Lob
    private String thumbnailUrl;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> itemImages;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVideo> itemVideos;

    public void setItemImages(List<ItemImage> itemImages) {
        this.itemImages = itemImages;
    }

    public void setItemVideos(List<ItemVideo> itemVideos) {
        this.itemVideos = itemVideos;
    }

    public void updateItem(ItemUpdate itemUpdate) {
        this.detail = itemUpdate.detail();
        this.redirectUrl = itemUpdate.redirectUrl();
        this.categoryType = itemUpdate.categoryType();
        this.price = itemUpdate.price();
        this.refund = itemUpdate.refund();
        this.rating = itemUpdate.rating();
        this.thumbnailUrl = itemUpdate.thumbnailUrl();
    }
}

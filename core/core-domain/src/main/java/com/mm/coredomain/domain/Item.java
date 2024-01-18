package com.mm.coredomain.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

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

    public void updateItem(ItemUpdate itemUpdate){
        this.detail = itemUpdate.detail();
        this.redirectUrl = itemUpdate.redirectUrl();
        this.categoryType = itemUpdate.categoryType();
        this.price = itemUpdate.price();
        this.refund = itemUpdate.refund();
        this.rating = itemUpdate.rating();
        this.thumbnailUrl = itemUpdate.thumbnailUrl();
    }
}

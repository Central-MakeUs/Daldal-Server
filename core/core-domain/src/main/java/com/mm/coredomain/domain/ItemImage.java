package com.mm.coredomain.domain;

import jakarta.persistence.*;

@Entity
public class ItemImage extends BaseEntity {
    @Id
    private Long id;

    @Lob
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Item item;
}

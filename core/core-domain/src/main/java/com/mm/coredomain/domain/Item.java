package com.mm.coredomain.domain;

import com.mm.coredomain.BaseEntity;
import jakarta.persistence.*;
import org.yaml.snakeyaml.events.Event;

@Entity
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
}

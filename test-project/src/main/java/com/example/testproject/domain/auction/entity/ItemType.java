package com.example.testproject.domain.auction.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ItemType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String name;

    @ManyToOne
    @JoinColumn(name = "item_parts_id")
    ItemParts itemParts;

    @Builder
    public ItemType(Long id, String name, ItemParts itemParts) {
        this.id = id;
        this.name = name;
        this.itemParts = itemParts;
    }
}

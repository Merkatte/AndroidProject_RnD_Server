package com.example.testproject.domain.auction.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    ItemType itemType;

    @ManyToOne
    @JoinColumn(name = "parts_id")
    ItemParts itemParts;


    @OneToMany(mappedBy = "item")
    List<ItemClasses> itemClasses = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    String description;

    @Builder
    public Item(Long id, String name, ItemParts itemParts, ItemType itemType, List<ItemClasses> itemClasses, String description) {
        this.id = id;
        this.name = name;
        this.itemParts = itemParts;
        this.itemType = itemType;
        this.itemClasses = itemClasses;
        this.description = description;
    }
}

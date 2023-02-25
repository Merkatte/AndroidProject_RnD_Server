package com.example.testproject.domain.auction.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class ItemRarity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String name;

    @Builder
    public ItemRarity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

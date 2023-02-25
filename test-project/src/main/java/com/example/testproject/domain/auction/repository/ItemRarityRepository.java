package com.example.testproject.domain.auction.repository;

import com.example.testproject.domain.auction.entity.ItemRarity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRarityRepository extends JpaRepository<ItemRarity, Long> {

    ItemRarity findByName(String name);

}

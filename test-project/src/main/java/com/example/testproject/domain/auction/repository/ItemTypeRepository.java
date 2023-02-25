package com.example.testproject.domain.auction.repository;

import com.example.testproject.domain.auction.entity.ItemParts;
import com.example.testproject.domain.auction.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {

    ItemType findByItemPartsAndName(ItemParts itemParts, String name);
    ItemType findByName(String name);

}

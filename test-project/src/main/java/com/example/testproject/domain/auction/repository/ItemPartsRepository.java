package com.example.testproject.domain.auction.repository;

import com.example.testproject.domain.auction.entity.ItemParts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPartsRepository extends JpaRepository<ItemParts, Long> {

    ItemParts findByName(String name);

}

package com.example.testproject.domain.auction.repository;

import com.example.testproject.domain.auction.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByName(String name);

    Optional<Item> findById(Long id);


}

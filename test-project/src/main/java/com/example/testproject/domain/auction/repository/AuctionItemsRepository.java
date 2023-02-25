package com.example.testproject.domain.auction.repository;

import com.example.testproject.domain.auction.entity.AuctionItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AuctionItemsRepository extends JpaRepository<AuctionItems, Long>, JpaSpecificationExecutor<AuctionItems> {
    List<AuctionItems> findAllByItem_Id(Long id);
    List<AuctionItems> findAllByAgility(Float agility);
    Page<AuctionItems> findAll(Pageable pageable);

}

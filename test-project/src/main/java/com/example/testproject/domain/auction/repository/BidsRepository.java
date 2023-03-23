package com.example.testproject.domain.auction.repository;

import com.example.testproject.domain.auction.entity.AuctionItems;
import com.example.testproject.domain.auction.entity.Bids;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidsRepository extends JpaRepository<Bids, Long> {

    Boolean existsByAppUserIdAndAuctionItemsId(Long userId, Long auctionItemsId);
    Bids findByAppUserIdAndAuctionItems(Long userId, AuctionItems auctionItem);
    List<Bids> findAllByAppUserId(Long userId);
}

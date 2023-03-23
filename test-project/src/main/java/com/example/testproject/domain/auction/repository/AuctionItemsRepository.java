package com.example.testproject.domain.auction.repository;

import com.example.testproject.domain.auction.entity.AuctionItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionItemsRepository extends JpaRepository<AuctionItems, Long>, JpaSpecificationExecutor<AuctionItems> {
    List<AuctionItems> findAllByAppUserId(Long userId);
    List<AuctionItems> findAllByAppUserIdAndCompleted(Long userId, Boolean completed);

//    @Query(value = "SELECT user_id, completed FROM auction_items as ai WHERE ai.id = :id", nativeQuery = true)
//    Object findAppUserIdAndCompletedById(Long id);
    Page<AuctionItems> findAll(Pageable pageable);
    @Modifying
    @Query("update AuctionItems ai set ai.highestBid = :gold where ai.id = :auctionItemsId")
    void updateHighestBid(Long auctionItemsId, Integer gold);

    @Modifying
    @Query("update AuctionItems ai set ai.completed = true where ai.id = :auctionItemsId")
    void updateCompleted(Long auctionItemsId);

    boolean existsById(Long id);

}

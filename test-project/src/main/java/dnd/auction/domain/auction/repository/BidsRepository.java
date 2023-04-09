package dnd.auction.domain.auction.repository;

import dnd.auction.domain.auction.entity.AuctionItems;
import dnd.auction.domain.auction.entity.Bids;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidsRepository extends JpaRepository<Bids, Long> {

    Boolean existsByAppUserIdAndAuctionItemsId(Long userId, Long auctionItemsId);
    Bids findByAppUserIdAndAuctionItems(Long userId, AuctionItems auctionItem);
    List<Bids> findAllByAppUserId(Long userId);
}

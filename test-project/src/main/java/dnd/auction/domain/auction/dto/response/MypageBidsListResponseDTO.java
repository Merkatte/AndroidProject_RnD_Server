package dnd.auction.domain.auction.dto.response;

import dnd.auction.domain.auction.entity.Bids;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MypageBidsListResponseDTO {
    Long id;
    Long auctionItemsId;
    Integer gold;
    LocalDateTime bidAt;

    public MypageBidsListResponseDTO(Bids bids) {
        this.id = bids.getId();
        this.auctionItemsId = bids.getAuctionItems().getId();
        this.gold = bids.getGold();
        this.bidAt = bids.getBidAt();
    }
}

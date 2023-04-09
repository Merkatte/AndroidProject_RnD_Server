package dnd.auction.domain.auction.dto.response;

import dnd.auction.domain.auction.entity.ItemRarity;
import dnd.auction.domain.auction.entity.AuctionItems;
import lombok.Getter;

@Getter
public class AuctionSearchResultResponseDTO {
    AuctionItems auctionItems;
    ItemRarity rarity;
    ItemResponseDTO item;

    public AuctionSearchResultResponseDTO(AuctionItems auctionItems){
        this.auctionItems = auctionItems;
        this.item = new ItemResponseDTO(auctionItems.getItem());
        this.rarity = auctionItems.getRarity();
    }

}

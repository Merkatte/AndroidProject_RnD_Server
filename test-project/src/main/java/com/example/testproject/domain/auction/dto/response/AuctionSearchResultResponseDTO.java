package com.example.testproject.domain.auction.dto.response;

import com.example.testproject.domain.auction.entity.AuctionItems;
import com.example.testproject.domain.auction.entity.ItemRarity;
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

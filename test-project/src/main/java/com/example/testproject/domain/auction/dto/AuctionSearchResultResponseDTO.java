package com.example.testproject.domain.auction.dto;

import com.example.testproject.domain.auction.entity.AuctionItems;
import lombok.Getter;

@Getter
public class AuctionSearchResultResponseDTO {
    AuctionItems auctionItems;
    ItemResponseDTO item;

    public AuctionSearchResultResponseDTO(AuctionItems auctionItems){
        this.auctionItems = auctionItems;
        this.item = new ItemResponseDTO(auctionItems.getItem());
    }

}

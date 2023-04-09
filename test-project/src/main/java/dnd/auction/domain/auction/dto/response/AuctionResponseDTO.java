package dnd.auction.domain.auction.dto.response;

import dnd.auction.domain.user.dto.UserResponseDTO;
import dnd.auction.domain.auction.entity.AuctionItems;
import lombok.Getter;

import java.util.List;

@Getter
public class AuctionResponseDTO {
    UserResponseDTO owner;
    AuctionItems auctionItems;
    ItemResponseDTO item;
    List<BidsResponseDTO> bids;

    public AuctionResponseDTO(AuctionItems auctionItems){
        this.owner = new UserResponseDTO(auctionItems.getAppUser());
        this.auctionItems = auctionItems;
        this.bids = auctionItems.getBids().stream().map(BidsResponseDTO::new).toList();
        this.item = new ItemResponseDTO(auctionItems.getItem());
    }
}

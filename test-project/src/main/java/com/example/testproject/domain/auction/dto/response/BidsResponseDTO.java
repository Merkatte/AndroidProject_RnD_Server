package com.example.testproject.domain.auction.dto.response;

import com.example.testproject.domain.auction.entity.Bids;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BidsResponseDTO {

    Long userId;
    String username;
    Integer gold;
    LocalDateTime bidAt;

    public BidsResponseDTO(Bids bids) {
        this.userId = bids.getAppUser().getId();
        this.username = bids.getAppUser().getUsername();
        this.gold = bids.getGold();
        this.bidAt = bids.getBidAt();
    }
}

package com.example.testproject.domain.auction.entity;

import com.example.testproject.domain.user.entity.AppUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Bids {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_items_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    AuctionItems auctionItems;

    @Column(nullable = false)
    Integer gold;

    @Column(nullable = false)
    LocalDateTime bidAt;

    @PrePersist
    private void setDefault() {
        this.bidAt = LocalDateTime.now();
    }

    @Builder
    public Bids(Long id, AppUser appUser, AuctionItems auctionItems, Integer gold, LocalDateTime bidAt) {
        this.id = id;
        this.appUser = appUser;
        this.auctionItems = auctionItems;
        this.gold = gold;
        this.bidAt = bidAt == null ? LocalDateTime.now() : bidAt;
    }
}

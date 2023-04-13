package dnd.auction.domain.auction.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import dnd.auction.domain.user.entity.AppUser;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "user_id")
    AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_items_id")
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

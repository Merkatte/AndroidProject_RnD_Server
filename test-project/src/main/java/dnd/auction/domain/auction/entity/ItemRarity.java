package dnd.auction.domain.auction.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ItemRarity {

    @Id
    Integer tier;

    @Column(nullable = false)
    String name;

    @Builder
    public ItemRarity(String name, Integer tier) {
        this.name = name;
        this.tier = tier;
    }
}

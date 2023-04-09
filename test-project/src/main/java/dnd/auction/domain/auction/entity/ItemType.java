package dnd.auction.domain.auction.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class ItemType {

    @Id
    String name;

    @Builder
    public ItemType(String name) {
        this.name = name;
    }

}

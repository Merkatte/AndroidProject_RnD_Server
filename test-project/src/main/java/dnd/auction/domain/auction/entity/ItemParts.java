package dnd.auction.domain.auction.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ItemParts {

    @Id
    String name;

    @Builder
    public ItemParts(Long id, String name) {
        this.name = name;
    }
}

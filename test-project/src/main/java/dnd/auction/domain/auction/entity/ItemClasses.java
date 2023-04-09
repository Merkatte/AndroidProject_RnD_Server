package dnd.auction.domain.auction.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ItemClasses {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;

    @ManyToOne
    @JoinColumn(name = "class_id")
    Classes classes;

    @Builder
    public ItemClasses(Long id, Item item, Classes classes) {
        this.id = id;
        this.item = item;
        this.classes = classes;
    }
}

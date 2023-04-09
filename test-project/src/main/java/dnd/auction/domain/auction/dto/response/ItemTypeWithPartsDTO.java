package dnd.auction.domain.auction.dto.response;

import dnd.auction.domain.auction.entity.ItemType;
import lombok.Getter;

@Getter
public class ItemTypeWithPartsDTO {
    String name;

    public ItemTypeWithPartsDTO(ItemType itemType) {
        this.name = itemType.getName();
    }
}

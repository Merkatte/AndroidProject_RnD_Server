package com.example.testproject.domain.auction.dto.response;

import com.example.testproject.domain.auction.entity.ItemType;
import lombok.Getter;

@Getter
public class ItemTypeWithPartsDTO {
    Long id;
    String itemParts;
    String name;

    public ItemTypeWithPartsDTO(ItemType itemType) {
        this.id = itemType.getId();
        this.itemParts = itemType.getItemParts().getName();
        this.name = itemType.getName();
    }
}

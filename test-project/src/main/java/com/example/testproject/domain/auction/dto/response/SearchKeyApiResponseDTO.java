package com.example.testproject.domain.auction.dto.response;

import com.example.testproject.domain.auction.entity.Item;
import com.example.testproject.domain.auction.entity.ItemParts;
import com.example.testproject.domain.auction.entity.ItemRarity;
import com.example.testproject.domain.auction.entity.ItemType;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchKeyApiResponseDTO {
    List<ItemParts> itemParts;
    List<ItemTypeWithPartsDTO> itemTypes;
    List<ItemRarity> itemRarities;
    List<ItemResponseDTO> items;

    public SearchKeyApiResponseDTO(List<ItemParts> itemParts, List<ItemType> itemTypes, List<ItemRarity> itemRarities, List<Item> items) {
        this.itemParts = itemParts;
        this.itemTypes = itemTypes.stream().map(ItemTypeWithPartsDTO::new).toList();
        this.itemRarities = itemRarities;
        this.items = items.stream().map(ItemResponseDTO::new).toList();
    }
}

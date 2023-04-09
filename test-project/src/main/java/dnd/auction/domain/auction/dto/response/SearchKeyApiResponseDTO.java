package dnd.auction.domain.auction.dto.response;

import dnd.auction.domain.auction.entity.Item;
import dnd.auction.domain.auction.entity.ItemRarity;
import dnd.auction.domain.auction.entity.ItemType;
import dnd.auction.domain.auction.entity.ItemParts;
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

package dnd.auction.domain.auction.service;

import dnd.auction.domain.auction.dto.request.ItemRarityRequestDTO;
import dnd.auction.domain.auction.dto.request.ItemRequestDTO;
import dnd.auction.domain.auction.entity.*;
import dnd.auction.domain.auction.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminWriteService {

    private final ItemTypeRepository itemTypeRepository;
    private final ItemRarityRepository itemRarityRepository;
    private final ItemPartsRepository itemPartsRepository;
    private final ItemRepository itemRepository;
    private final ClassesRepository classesRepository;
    private final ItemClassesRepository itemClassesRepository;

    public ItemType registerItemType(String itemType, String itemParts){
        return itemTypeRepository.save(ItemType.builder().name(itemType).build());
    }

    public ItemRarity registerItemRarity(ItemRarityRequestDTO itemRarityCommand){
        return itemRarityRepository.save(ItemRarity.builder().tier(itemRarityCommand.getTier()).name(itemRarityCommand.getName()).build());
    }

    public ItemParts registerItemParts(String itemParts){
        return itemPartsRepository.save(ItemParts.builder().name(itemParts).build());
    }

    public Classes registerClasses(String classes){
        return classesRepository.save(Classes.builder().name(classes).build());
    }

    public Item registerItem(ItemRequestDTO itemDTO){
        ItemParts parts = itemPartsRepository.findByName(itemDTO.getItemParts());
        ItemType type = itemTypeRepository.findByName(itemDTO.getItemType());
        var item = itemRepository.save(
                Item.builder()
                        .name(itemDTO.getName())
                        .itemType(type)
                        .itemParts(parts)
                        .build()
        );
        List<Classes> classes = itemDTO.getItemClasses().stream().map(classesRepository::findByName).toList();
        classes.forEach(
                c -> itemClassesRepository.save(ItemClasses.builder().classes(c).item(item).build())
        );
        return item;

    }
}

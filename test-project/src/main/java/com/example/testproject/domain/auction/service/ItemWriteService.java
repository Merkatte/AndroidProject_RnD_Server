package com.example.testproject.domain.auction.service;

import com.example.testproject.domain.auction.dto.ItemRequestDTO;
import com.example.testproject.domain.auction.entity.*;
import com.example.testproject.domain.auction.repository.*;
import com.example.testproject.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemWriteService {

    final private ItemTypeRepository itemTypeRepository;
    final private ItemRarityRepository itemRarityRepository;
    final private ClassesRepository classesRepository;
    private final ItemRepository itemRepository;
    private final ItemClassesRepository itemClassesRepository;
    final private ItemPartsRepository itemPartsRepository;
    private final AuctionItemsRepository auctionItemsRepository;
    private final BidsRepository bidsRepository;

    public ItemType registerItemType(String itemType, String itemParts){
        return itemTypeRepository.save(ItemType.builder().name(itemType).itemParts(itemPartsRepository.findByName(itemParts)).build());
    }

    public ItemRarity registerItemRarity(String itemRarity){
        return itemRarityRepository.save(ItemRarity.builder().name(itemRarity).build());
    }

    public ItemParts registerItemParts(String itemParts){
        return itemPartsRepository.save(ItemParts.builder().name(itemParts).build());
    }

    public Item registerItem(ItemRequestDTO itemDTO){
        ItemParts parts = itemPartsRepository.findByName(itemDTO.getItemParts());
        ItemType type = itemTypeRepository.findByItemPartsAndName(parts, itemDTO.getItemType());
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
    public AuctionItems registerAuctionItem(AuctionItems auctionItems){
        return auctionItemsRepository.save(auctionItems);
    }

    public Bids bidsItem(Long auctionItemsId, Bids bids){
        AuctionItems auctionItem = auctionItemsRepository.findById(auctionItemsId).orElseThrow();
        if (Objects.equals(bids.getAppUser().getId(), auctionItem.getAppUser().getId())){
            throw new CustomException("경매 주인은 입찰할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
        return bidsRepository.save(bids);
    }
}

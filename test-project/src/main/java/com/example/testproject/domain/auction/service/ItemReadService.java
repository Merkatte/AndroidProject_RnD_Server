package com.example.testproject.domain.auction.service;

import com.example.testproject.domain.auction.dto.response.AuctionResponseDTO;
import com.example.testproject.domain.auction.dto.response.AuctionSearchResultResponseDTO;
import com.example.testproject.domain.auction.dto.response.ItemResponseDTO;
import com.example.testproject.domain.auction.dto.response.SearchKeyApiResponseDTO;
import com.example.testproject.domain.auction.entity.*;
import com.example.testproject.domain.auction.repository.*;
import com.example.testproject.domain.auction.specification.AuctionItemsSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemReadService {

    final private ItemRepository itemRepository;
    private final AuctionItemsRepository auctionItemsRepository;
    private final ItemTypeRepository itemTypeRepository;
    private final ItemPartsRepository itemPartsRepository;
    private final ItemRarityRepository itemRarityRepository;

    public List<ItemType> getItemType(Long partsId){
        return itemTypeRepository.findByItemPartsId(partsId);
    }

    public List<ItemParts> getItemParts(){
        return itemPartsRepository.findAll();
    }

    public List<ItemRarity> getItemRarity(){
        return itemRarityRepository.findAll();
    }
    public List<ItemResponseDTO> getItems(){
        var items = itemRepository.findAll();
        System.out.println(items.size());
        return items.stream().map(ItemResponseDTO::new).toList();
    }

    public List<AuctionSearchResultResponseDTO> getAuctionItems(){
        var auctionItems = auctionItemsRepository.findAll();
        return auctionItems.stream().map(AuctionSearchResultResponseDTO::new).toList();
    }

    public List<AuctionSearchResultResponseDTO> getAuctionItemsSearch(Map<String, String> options, Pageable pageable){
        Specification<AuctionItems> spec = Specification.where(null);

        //option key type 별 specification search
        //String : itemName
        //Integer : itemCategory (itemType, itemParts)
        //Float : itemOptions
        for (Map.Entry<String, String> entry : options.entrySet()){
            String option = entry.getKey();
            if (Objects.equals(option, "page") || Objects.equals(option, "size") || Objects.equals(option, "order")){ // Pageable parameter 제외
                continue;
            }
            else if(Objects.equals(option, "itemName")){
                Item item = itemRepository.findByName(entry.getValue());
                spec = spec.and(AuctionItemsSpecification.equalItemName("item", item));
            }
            else if(!entry.getValue().contains(".")){
                Long value = Long.parseLong(entry.getValue());
                spec = spec.and(AuctionItemsSpecification.equalItemCategory(option, value));
            }
            else{
                Float value = Float.parseFloat(entry.getValue());
                spec = spec.and(AuctionItemsSpecification.greaterThanOptionValue(option, value));
            }

        }

        Page<AuctionItems> searchItems =  auctionItemsRepository.findAll(spec, pageable);
        return searchItems.stream().map(AuctionSearchResultResponseDTO::new).toList();

    }

    public AuctionResponseDTO getAuctionItem(Long auctionItemsId){
        AuctionItems auctionItem = auctionItemsRepository.findById(auctionItemsId).orElseThrow();
        return new AuctionResponseDTO(auctionItem);
    }

    public SearchKeyApiResponseDTO getSearchKeyApi() {
        List<ItemType> itemTypes = itemTypeRepository.findAll();
        List<ItemParts> itemParts = itemPartsRepository.findAll();
        List<ItemRarity> itemRarities = itemRarityRepository.findAll();
        List<Item> items = itemRepository.findAll();
        return new SearchKeyApiResponseDTO(itemParts, itemTypes, itemRarities, items);
    }

}

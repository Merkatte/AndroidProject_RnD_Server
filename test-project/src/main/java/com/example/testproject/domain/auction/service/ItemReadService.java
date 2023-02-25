package com.example.testproject.domain.auction.service;

import com.example.testproject.domain.auction.dto.AuctionResponseDTO;
import com.example.testproject.domain.auction.dto.AuctionSearchResultResponseDTO;
import com.example.testproject.domain.auction.dto.ItemResponseDTO;
import com.example.testproject.domain.auction.entity.AuctionItems;
import com.example.testproject.domain.auction.entity.Item;
import com.example.testproject.domain.auction.repository.AuctionItemsRepository;
import com.example.testproject.domain.auction.repository.ItemRepository;
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

    public List<ItemResponseDTO> getItems(){
        var items = itemRepository.findAll();
        System.out.println(items.size());
        return items.stream().map(ItemResponseDTO::new).toList();
    }

    public List<AuctionSearchResultResponseDTO> getAuctionItems(){
        var auctionItems = auctionItemsRepository.findAll();
        return auctionItems.stream().map(AuctionSearchResultResponseDTO::new).toList();
    }

    public List<AuctionSearchResultResponseDTO> getAuctionItemsSearch(String itemName, Map<String, String> options, Pageable pageable){
        Item item = itemRepository.findByName(itemName);
        Specification<AuctionItems> spec = Specification.where(AuctionItemsSpecification.equalSearchKey("item", item));
        for (Map.Entry<String, String> entry : options.entrySet()){
            String option = entry.getKey();
            if (Objects.equals(option, "page") || Objects.equals(option, "size") || Objects.equals(option, "order")){
                continue;
            }
            Float value = Float.parseFloat(entry.getValue());
            spec = spec.and(AuctionItemsSpecification.greaterThanOptionValue(option, value));
        }

        Page<AuctionItems> searchItems =  auctionItemsRepository.findAll(spec, pageable);
        return searchItems.stream().map(AuctionSearchResultResponseDTO::new).toList();

    }

    public AuctionResponseDTO getAuctionItem(Long auctionItemsId){
        var auctionItem = auctionItemsRepository.findById(auctionItemsId).orElseThrow();
        return new AuctionResponseDTO(auctionItem);
    }
}

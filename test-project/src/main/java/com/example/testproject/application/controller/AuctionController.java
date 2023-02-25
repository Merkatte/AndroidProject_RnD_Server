package com.example.testproject.application.controller;

import com.example.testproject.domain.auction.dto.*;
import com.example.testproject.domain.auction.entity.*;
import com.example.testproject.domain.auction.service.ClassesWriteService;
import com.example.testproject.domain.auction.service.ItemReadService;
import com.example.testproject.domain.auction.service.ItemWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {

    final private ItemWriteService itemWriteService;
    final private ItemReadService itemReadService;
    final private ClassesWriteService classesWriteService;

    @PostMapping("/item-type")
    public ItemType registerItemType(@RequestBody ItemTypeRequestDTO itemTypeCommand){
        return itemWriteService.registerItemType(itemTypeCommand.name(), itemTypeCommand.partsName());
    }

    @GetMapping("/item-type")
    public List<ItemType> getItemType(@RequestParam("partsId") Long partsId){
        return itemReadService.getItemType(partsId);
    }

    @PostMapping("/item-rarity")
    public ItemRarity registerItemType(@RequestBody ItemRarityRequestDTO itemRarityCommand){
        return itemWriteService.registerItemRarity(itemRarityCommand.name());
    }

    @PostMapping("/item-parts")
    public ItemParts registerItemParts(@RequestBody ItemPartsRequestDTO itemPartsCommand){
        return itemWriteService.registerItemParts(itemPartsCommand.name());
    }

    @GetMapping("/item-parts")
    public List<ItemParts> getItemParts(){
        return itemReadService.getItemParts();
    }

    @PostMapping("/classes")
    public Classes regusterClasses(@RequestBody ClassesRequestDTO classesCommand){
        return classesWriteService.registerClasses(classesCommand.name());
    }

    @PostMapping("/item")
    public Item registerItem(@RequestBody ItemRequestDTO itemDTO){
        return itemWriteService.registerItem(itemDTO);
    }

    @GetMapping("/get-items")
    public List<ItemResponseDTO> getItems(){
        return itemReadService.getItems();
    }

    @PostMapping("/register-item")
    public AuctionItems registerAuctionItem(@RequestBody AuctionItems auctionItems){
        return itemWriteService.registerAuctionItem(auctionItems);
    }

    @GetMapping("/get-auction-items")
    public List<AuctionSearchResultResponseDTO> getAuctionItems(){
        return itemReadService.getAuctionItems();
    }

    @GetMapping("/search/{itemName}")
    public List<AuctionSearchResultResponseDTO> getAuctionItemsSearch(@PathVariable String itemName,
                                                                      Pageable pageable,
                                                          @RequestParam Map<String, String> options){

        return itemReadService.getAuctionItemsSearch(itemName, options, pageable);
    }

    @GetMapping("/{auctionItemsId}")
    public AuctionResponseDTO getAuctionItem(@PathVariable("auctionItemsId") Long auctionItemsId){
        return itemReadService.getAuctionItem(auctionItemsId);
    }

    @PostMapping("/{auctionItemsId}/bids")
    public Bids bidsItem(@PathVariable Long auctionItemsId, @RequestBody Bids bids){
        return itemWriteService.bidsItem(auctionItemsId, bids);
    }

}

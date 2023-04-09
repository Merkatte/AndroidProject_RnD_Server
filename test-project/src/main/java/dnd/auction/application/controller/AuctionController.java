package dnd.auction.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dnd.auction.domain.auction.dto.request.*;
import dnd.auction.domain.auction.dto.response.AuctionResponseDTO;
import dnd.auction.domain.auction.dto.response.AuctionSearchResultResponseDTO;
import dnd.auction.domain.auction.dto.response.ItemResponseDTO;
import dnd.auction.domain.auction.entity.*;
import dnd.auction.domain.auction.service.ClassesWriteService;
import dnd.auction.domain.auction.service.ItemReadService;
import dnd.auction.domain.auction.service.ItemWriteService;
import dnd.auction.domain.auction.service.SearchKeyReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {

    final private ItemWriteService itemWriteService;
    final private ItemReadService itemReadService;
    final private ClassesWriteService classesWriteService;
    private final SearchKeyReadService searchKeyReadService;

    // ADMIN Insert/Update Item Categories
    @PostMapping("/item-type")
    public ItemType registerItemType(@RequestBody ItemTypeRequestDTO itemTypeCommand){
        return itemWriteService.registerItemType(itemTypeCommand.name(), itemTypeCommand.partsName());
    }

    @PostMapping("/item-rarity")
    public ItemRarity registerItemType(@RequestBody ItemRarityRequestDTO itemRarityCommand){
        return itemWriteService.registerItemRarity(itemRarityCommand);
    }

    @PostMapping("/item-parts")
    public ItemParts registerItemParts(@RequestBody ItemPartsRequestDTO itemPartsCommand){
        return itemWriteService.registerItemParts(itemPartsCommand.name());
    }
    @PostMapping("/classes")
    public Classes regusterClasses(@RequestBody ClassesRequestDTO classesCommand){
        return classesWriteService.registerClasses(classesCommand.name());
    }
    @PostMapping("/item")
    public Item registerItem(@RequestBody ItemRequestDTO itemDTO){
        return itemWriteService.registerItem(itemDTO);
    }
    //-----------------------------------------------------------------------------------------------//

    // GET Item APIs
    @GetMapping("/item-searchKeys")
    public String getSearchKeyApi() throws JsonProcessingException {
        return searchKeyReadService.getSearchKeyApi();
    }
    @GetMapping("/item-type")
    public List<ItemType> getItemType(){ return searchKeyReadService.getItemType(); }
    @GetMapping("/item-rarities")
    public List<ItemRarity> getItemRarity(){
        return searchKeyReadService.getItemRarity();
    }
    @GetMapping("/item-parts")
    public List<ItemParts> getItemParts(){
        return searchKeyReadService.getItemParts();
    }
    @GetMapping("/item")
    public List<ItemResponseDTO> getItems(){
        return searchKeyReadService.getItems();
    }
    //-----------------------------------------------------------------------------------------------//
    @PostMapping("/register-item")
    public AuctionItems registerAuctionItem(@RequestBody AuctionItems auctionItems){
        return itemWriteService.registerAuctionItem(auctionItems);
    }

    @GetMapping("/get-auction-items")
    public List<AuctionSearchResultResponseDTO> getAuctionItems(){
        return itemReadService.getAuctionItems();
    }

    @GetMapping("/search")
    public List<AuctionSearchResultResponseDTO> getAuctionItemsSearch(Pageable pageable,
                                                          @RequestParam Map<String, String> options){
        options.remove("page");
        options.remove("size"); // params 에서 pageable 관련 key 삭제
        return itemReadService.getAuctionItemsSearch(options, pageable);

    }
    @GetMapping("/{auctionItemsId}")
    public AuctionResponseDTO getAuctionItem(@PathVariable("auctionItemsId") Long auctionItemsId){
        return itemReadService.getAuctionItem(auctionItemsId);
    }

    @PostMapping("/{auctionItemsId}/bids")
    public Long bidsItem(@PathVariable("auctionItemsId") Long auctionItemsId, @RequestBody Bids bids){
        Bids newBid = itemWriteService.bidsItem(auctionItemsId, bids);
        return newBid.getAuctionItems().getId();
    }

    @PostMapping("/{auctionItemsId}/accepted")
    public void bidsAccepted(HttpServletRequest req, @PathVariable("auctionItemsId") Long auctionItemsId, @RequestBody BidsAcceptedRequestDTO bids){
        Long bidsId = bids.getBidsId();
        itemWriteService.bidsAccepted(req, auctionItemsId, bidsId);
    }

}

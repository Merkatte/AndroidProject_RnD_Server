package dnd.auction.application.controller.auction;

import dnd.auction.domain.auction.dto.response.AuctionResponseDTO;
import dnd.auction.domain.auction.dto.response.AuctionSearchResultResponseDTO;
import dnd.auction.domain.auction.entity.AuctionItems;
import dnd.auction.domain.auction.service.AuctionReadService;
import dnd.auction.domain.auction.service.AuctionWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {

    final private AuctionWriteService itemWriteService;
    final private AuctionReadService itemReadService;

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

}

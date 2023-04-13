package dnd.auction.application.controller.auction;

import com.fasterxml.jackson.core.JsonProcessingException;
import dnd.auction.domain.auction.dto.response.ItemResponseDTO;
import dnd.auction.domain.auction.entity.ItemParts;
import dnd.auction.domain.auction.entity.ItemRarity;
import dnd.auction.domain.auction.entity.ItemType;
import dnd.auction.domain.auction.service.SearchKeyReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auction/api")
@RequiredArgsConstructor
public class AuctionApiController {

    private final SearchKeyReadService searchKeyReadService;

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

}

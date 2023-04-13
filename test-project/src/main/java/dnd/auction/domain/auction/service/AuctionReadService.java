package dnd.auction.domain.auction.service;

import dnd.auction.domain.auction.dto.response.AuctionResponseDTO;
import dnd.auction.domain.auction.dto.response.AuctionSearchResultResponseDTO;
import dnd.auction.domain.auction.entity.AuctionItems;
import dnd.auction.domain.auction.entity.Item;
import dnd.auction.domain.auction.entity.ItemParts;
import dnd.auction.domain.auction.entity.ItemType;
import dnd.auction.domain.auction.repository.AuctionItemsRepository;
import dnd.auction.domain.auction.repository.ItemPartsRepository;
import dnd.auction.domain.auction.repository.ItemRepository;
import dnd.auction.domain.auction.repository.ItemTypeRepository;
import dnd.auction.domain.auction.specification.AuctionItemsSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuctionReadService {

    private final ItemRepository itemRepository;
    private final AuctionItemsRepository auctionItemsRepository;
    private final ItemTypeRepository itemTypeRepository;
    private final ItemPartsRepository itemPartsRepository;

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
            String searchKey = entry.getKey();
            try{
                // searchKey가 변동 옵션일 경우 (Float 타입의 value)
                Float value = Float.parseFloat(entry.getValue());
                spec = spec.and(AuctionItemsSpecification.greaterThanOptionValue(searchKey, value));

            }catch(NumberFormatException e){
                // searchKey가 카테고리일 경우 (String 타입의 value)
                switch (searchKey) {
                    case "itemName" -> {
                        Item item = itemRepository.findByName(entry.getValue());
                        spec = spec.and(AuctionItemsSpecification.equalItem("item", item));
                    }
                    case "itemType" -> {
                        ItemType itemType = itemTypeRepository.findByName(entry.getValue());
                        spec = spec.and(AuctionItemsSpecification.equalItemType("itemType", itemType));
                    }
                    case "itemParts" -> {
                        ItemParts itemParts = itemPartsRepository.findByName(entry.getValue());
                        spec = spec.and(AuctionItemsSpecification.equalItemParts("itemParts", itemParts));
                    }
                    case "completed" -> {
                        boolean isCompleted = Boolean.parseBoolean(entry.getValue());
                        spec = spec.and(AuctionItemsSpecification.filterCompleted(searchKey, isCompleted));
                    }
                };
            }
        }
        // spec 캐싱??

        Page<AuctionItems> searchItems =  auctionItemsRepository.findAll(spec, pageable);
        return searchItems.stream().map(AuctionSearchResultResponseDTO::new).toList();

    }

    public AuctionResponseDTO getAuctionItem(Long auctionItemsId){
        AuctionItems auctionItem = auctionItemsRepository.findById(auctionItemsId).orElseThrow();
        return new AuctionResponseDTO(auctionItem);
    }

}

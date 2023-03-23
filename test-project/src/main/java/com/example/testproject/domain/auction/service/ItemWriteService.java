package com.example.testproject.domain.auction.service;

import com.example.testproject.domain.auction.dto.request.ItemRarityRequestDTO;
import com.example.testproject.domain.auction.dto.request.ItemRequestDTO;
import com.example.testproject.domain.auction.entity.*;
import com.example.testproject.domain.auction.repository.*;
import com.example.testproject.domain.user.security.JwtTokenProvider;
import com.example.testproject.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    private final JwtTokenProvider jwtTokenProvider;

    public ItemType registerItemType(String itemType, String itemParts){
        return itemTypeRepository.save(ItemType.builder().name(itemType).itemParts(itemPartsRepository.findByName(itemParts)).build());
    }

    public ItemRarity registerItemRarity(ItemRarityRequestDTO itemRarityCommand){
        return itemRarityRepository.save(ItemRarity.builder().tier(itemRarityCommand.getTier()).name(itemRarityCommand.getName()).build());
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

    @Transactional
    public Bids bidsItem(Long auctionItemsId, Bids bids){

        // 없는 경매 물품일 경우
        AuctionItems auctionItem = auctionItemsRepository.findById(auctionItemsId).orElseThrow(() ->
                new CustomException("not exist auction item", HttpStatus.BAD_REQUEST));

        // 최고 입찰금 보다 적을 경우 throw
        if (auctionItem.getHighestBid() > bids.getGold())
            throw new CustomException("you must bid higher than highest bid", HttpStatus.BAD_REQUEST);

        Long bidderId = bids.getAppUser().getId();
        Long sellerId = auctionItem.getAppUser().getId();

        if (bidderId.equals(sellerId)) {
            throw new CustomException("경매 주인은 입찰할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
        Bids existBid = bidsRepository.findByAppUserIdAndAuctionItems(bidderId, auctionItem);

        // 최고 입찰금 갱신
        auctionItemsRepository.updateHighestBid(auctionItemsId, bids.getGold());

        // 입찰자가 물품에 입찰 기록이 있을 경우 해당 입찰 기록 갱신
        if (existBid != null){
            return bidsRepository.save(Bids.builder()
                            .id(existBid.getId())
                            .appUser(bids.getAppUser())
                            .auctionItems(bids.getAuctionItems())
                            .gold(bids.getGold()).build());
        }

        return bidsRepository.save(Bids.builder()
                .appUser(bids.getAppUser())
                .auctionItems(bids.getAuctionItems())
                .gold(bids.getGold())
                .build());
    }

    @Transactional
    public void bidsAccepted(HttpServletRequest req, Long auctionItemsId, Long bidsId){
        //TODO 낙찰된 사용자에게 알림 전송(Websocket 이용한 실시간 알림 시스템 구축 필요)
        String accessToken = jwtTokenProvider.resolveToken(req);
        Long userId = jwtTokenProvider.getId(accessToken);

        AuctionItems auctionItem = auctionItemsRepository.findById(auctionItemsId).orElseThrow();

        if (!Objects.equals(userId, auctionItem.getAppUser().getId())) // 판매자 인증 절차
            throw new CustomException("access denied", HttpStatus.BAD_REQUEST);
        if (auctionItem.getCompleted()) // 이미 완료된 거래
            throw new CustomException("already completed", HttpStatus.BAD_REQUEST);

        auctionItemsRepository.updateCompleted(auctionItemsId);
    }
}

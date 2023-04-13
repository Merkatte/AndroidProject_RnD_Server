package dnd.auction.domain.auction.service;

import dnd.auction.domain.auction.entity.AuctionItems;
import dnd.auction.domain.auction.repository.AuctionItemsRepository;
import dnd.auction.domain.auction.repository.BidsRepository;
import dnd.auction.domain.notification.service.NotificationService;
import dnd.auction.domain.user.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionWriteService {

    private final AuctionItemsRepository auctionItemsRepository;
    private final BidsRepository bidsRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final NotificationService notificationService;

    @Transactional
    public AuctionItems registerAuctionItem(AuctionItems auctionItems){
        return auctionItemsRepository.save(auctionItems);
    }

}

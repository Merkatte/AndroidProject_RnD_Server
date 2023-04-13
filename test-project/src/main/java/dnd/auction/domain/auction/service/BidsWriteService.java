package dnd.auction.domain.auction.service;

import dnd.auction.domain.auction.entity.AuctionItems;
import dnd.auction.domain.auction.entity.Bids;
import dnd.auction.domain.auction.repository.AuctionItemsRepository;
import dnd.auction.domain.auction.repository.BidsRepository;
import dnd.auction.domain.notification.entity.NotificationMessage;
import dnd.auction.domain.notification.service.NotificationService;
import dnd.auction.domain.user.security.JwtTokenProvider;
import dnd.auction.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BidsWriteService {

    private final AuctionItemsRepository auctionItemsRepository;
    private final BidsRepository bidsRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final NotificationService notificationService;

    @Transactional
    public Bids bidsItem(HttpServletRequest req, Bids bids){

        Long bidderId = bids.getAppUser().getId();
        AuctionItems auctionItems = validateBids(req, bids);

        Bids existBid = bidsRepository.findByAppUserIdAndAuctionItems(bidderId, auctionItems);

        // 최고 입찰금 갱신
        auctionItemsRepository.updateHighestBid(auctionItems.getId(), bids.getGold());

        // 입찰자가 물품에 입찰 기록이 있을 경우 해당 입찰 기록 갱신
        if (existBid != null){
            try{
                return bidsRepository.save(Bids.builder()
                        .id(existBid.getId())
                        .appUser(existBid.getAppUser())
                        .auctionItems(existBid.getAuctionItems())
                        .gold(bids.getGold())
                        .build());
            }catch(DataIntegrityViolationException e){
                throw new CustomException("database error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        try{
            return bidsRepository.save(bids);
        }catch(DataIntegrityViolationException e){
            throw new CustomException("database error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void bidsAwarded(HttpServletRequest req, Long auctionItemsId, Long bidsId) throws IOException {
        String accessToken = jwtTokenProvider.resolveToken(req);
        AuctionItems auctionItem = auctionItemsRepository.findById(auctionItemsId).orElseThrow();
        Bids bids = bidsRepository.findById(bidsId).orElseThrow();

        Long seller = jwtTokenProvider.getId(accessToken);
        Long buyer = bids.getAppUser().getId();

        if (!Objects.equals(seller, auctionItem.getAppUser().getId())) // 판매자 인증 절차
            throw new CustomException("access denied", HttpStatus.BAD_REQUEST);
        if (auctionItem.getCompleted()) // 이미 완료된 거래
            throw new CustomException("already completed", HttpStatus.BAD_REQUEST);

        NotificationMessage message = NotificationMessage.builder()
                .message("you awarded bid")
                .userId(buyer.toString())
                .auctionItemsId(auctionItem.getId())
                .build();

        notificationService.BidAwardNotification(buyer.toString(), message);
        auctionItemsRepository.updateCompleted(auctionItemsId);

    }

    public AuctionItems validateBids(HttpServletRequest req, Bids bids){

        // bidder 정보 유효성 검사
        String accessToken = jwtTokenProvider.resolveToken(req);
        Long tokenProviderId = jwtTokenProvider.getId(accessToken);
        if (!bids.getAppUser().getId().equals(tokenProviderId)){
            throw new CustomException("bidder information unmatched", HttpStatus.BAD_REQUEST);
        }

        // 없는 경매 물품일 경우
        AuctionItems auctionItems = auctionItemsRepository.findById(bids.getAuctionItems().getId()).orElseThrow(() ->
                new CustomException("not exist auction item", HttpStatus.BAD_REQUEST));

        // 최고 입찰금 보다 적을 경우 throw
        if (auctionItems.getHighestBid() >= bids.getGold())
            throw new CustomException("you must bid higher than highest bid", HttpStatus.BAD_REQUEST);

        if (bids.getAppUser().getId().equals(auctionItems.getAppUser().getId())) {
            throw new CustomException("경매 주인은 입찰할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        return auctionItems;
    }
}

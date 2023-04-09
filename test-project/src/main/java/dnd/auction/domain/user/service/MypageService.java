package dnd.auction.domain.user.service;

import dnd.auction.domain.auction.dto.response.MypageBidsListResponseDTO;
import dnd.auction.domain.auction.entity.AuctionItems;
import dnd.auction.domain.auction.entity.Bids;
import dnd.auction.domain.auction.repository.AuctionItemsRepository;
import dnd.auction.domain.auction.repository.BidsRepository;
import dnd.auction.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final BidsRepository bidsRepository;
    private final AuctionItemsRepository auctionItemsRepository;

    public List<MypageBidsListResponseDTO> getMypageBids(Long bidderId){
        List<Bids> bidsList = bidsRepository.findAllByAppUserId(bidderId);
        return bidsList.stream().map(MypageBidsListResponseDTO::new).toList();
    }

    public List<AuctionItems> getMypageAuctionItems(Long userId, String filter){

        if (Objects.equals(filter, "all")){
            return auctionItemsRepository.findAllByAppUserId(userId);
        } else if (Objects.equals(filter, "completed")) {
            return auctionItemsRepository.findAllByAppUserIdAndCompleted(userId, true);
        } else if (Objects.equals(filter, "uncompleted")) {
            return auctionItemsRepository.findAllByAppUserIdAndCompleted(userId, false);
        }
        throw new CustomException("wrong filter name", HttpStatus.BAD_REQUEST);
    }


}

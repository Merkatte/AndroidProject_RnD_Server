package dnd.auction.application.controller.user;

import dnd.auction.domain.auction.dto.response.MypageBidsListResponseDTO;
import dnd.auction.domain.user.service.MypageService;
import dnd.auction.domain.auction.entity.AuctionItems;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;
    @GetMapping("/bid-list/{userId}")
    public List<MypageBidsListResponseDTO> getMypageBids(@PathVariable("userId") Long bidderId){
        return mypageService.getMypageBids(bidderId);
    }

    @GetMapping("/auction-list/{userId}")
    public List<AuctionItems> getMypageAuctionItems(@PathVariable("userId") Long userId, @RequestParam("filter") String filter){
        return mypageService.getMypageAuctionItems(userId, filter);
    }

}

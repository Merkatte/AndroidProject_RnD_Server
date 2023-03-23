package com.example.testproject.application.controller.user;

import com.example.testproject.domain.auction.dto.response.MypageBidsListResponseDTO;
import com.example.testproject.domain.auction.entity.AuctionItems;
import com.example.testproject.domain.user.service.MypageService;
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

package dnd.auction.application.controller.auction;

import dnd.auction.domain.auction.dto.request.BidsAcceptedRequestDTO;
import dnd.auction.domain.auction.entity.Bids;
import dnd.auction.domain.auction.service.BidsWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class BidsController {

    private final BidsWriteService bidsWriteService;
    @PostMapping("/bids")
    public Bids bidsItem(HttpServletRequest req, @RequestBody Bids bids){
        return bidsWriteService.bidsItem(req, bids);
    }

    @PostMapping("/{auctionItemsId}/bids/awarded")
    public void bidsAccepted(HttpServletRequest req, @PathVariable("auctionItemsId") Long auctionItemsId, @RequestBody BidsAcceptedRequestDTO bids) throws IOException {
        Long bidsId = bids.getBidsId();
        bidsWriteService.bidsAwarded(req, auctionItemsId, bidsId);
    }

}

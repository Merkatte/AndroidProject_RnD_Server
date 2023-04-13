package dnd.auction.domain.notification.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationMessage {
    String userId;
    Long auctionItemsId;
    String message;

//    @DateTimeFormat(pattern = "yy-MM-ddTHH:mm:ss")
//    LocalDateTime notifiedAt;

    @Builder
    public NotificationMessage(String userId, Long auctionItemsId, String message) {
        this.userId = userId;
        this.auctionItemsId = auctionItemsId;
        this.message = message;
    }
}

package dnd.auction.application.controller.notification;

import dnd.auction.domain.notification.entity.NotificationMessage;
import dnd.auction.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

//    @MessageMapping("user/{userId}/send-notification")
//    public void sendNotification(@DestinationVariable String userId, NotificationMessage notificationMessage) throws IOException {
//        System.out.println("send notification success and userId : " + userId);
//        String destination = "/user/" + userId + "/receive-notification";
//        messagingTemplate.convertAndSend(destination, notificationMessage);
//
//    }

    @PostMapping("/notification-test")
    public void sendTestNotification(){
        NotificationMessage message = NotificationMessage.builder()
                .message("success")
                .auctionItemsId(1L)
                .userId("1")
                .build();
        notificationService.BidAwardNotification("1", message);
    }

}

package dnd.auction.domain.notification.service;

import dnd.auction.domain.notification.entity.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RedisTemplate<String, String> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    public void BidAwardNotification(String userId, NotificationMessage notificationMessage) {
        System.out.println("send notification success and userId : " + userId);
        String destination = "/user/" + userId + "/receive-notification";
        messagingTemplate.convertAndSend(destination, notificationMessage);

    }
}

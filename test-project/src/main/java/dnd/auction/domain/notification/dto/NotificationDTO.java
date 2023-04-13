package dnd.auction.domain.notification.dto;

import lombok.Getter;

@Getter
public class NotificationDTO {
    String message;
    Long senderId;
    Long receiverId;
}

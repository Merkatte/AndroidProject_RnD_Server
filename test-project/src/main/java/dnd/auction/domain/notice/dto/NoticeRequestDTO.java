package dnd.auction.domain.notice.dto;

import lombok.Getter;

@Getter
public class NoticeRequestDTO {
    String message;
    Long senderId;
    Long receiverId;
}

package dnd.auction.chatService.domain.chat.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class ChatRoomSession {

    @Id
    String sessionId;

    @Column(nullable = false)
    Long userId;

    @Column(nullable = false)
    Long opponentId;

    @Column
    LocalDateTime createdAt;

}

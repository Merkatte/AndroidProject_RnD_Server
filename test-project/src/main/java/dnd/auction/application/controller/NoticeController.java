package dnd.auction.application.controller;

import dnd.auction.domain.notice.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    final private SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/{userId}")
    public void sendToSpecificUser(@Payload Message message){
        simpMessageSendingOperations.convertAndSendToUser(message.getTo(), "/specific", message);
    }
}

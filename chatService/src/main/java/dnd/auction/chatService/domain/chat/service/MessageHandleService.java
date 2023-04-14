package dnd.auction.chatService.domain.chat.service;

import dnd.auction.chatService.domain.chat.entity.ChatMessage;
import dnd.auction.chatService.domain.chat.entity.ChatRoomSession;
import dnd.auction.chatService.domain.chat.repository.ChatMessageRepository;
import dnd.auction.chatService.domain.chat.repository.ChatRoomSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MessageHandleService {

    private final ChatMessageRepository chatMessageRepository;
    private final RedisTemplate<String, ChatMessage> redisTemplate;
    private final ChatRoomSessionRepository chatRoomSessionRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Value("${batch.time}")
    private int batchTime;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private static final String CACHE_CHAT_ROOM_PREFIX = "cached sessionId : "; // 영속화된 데이터 캐시
    private static final String CACHE_NEW_MESSAGE_PREFIX = "cached new chat sessionId : "; // 신규 메시지 캐시


    public List<ChatMessage> getChatRoomMessages(String sessionId){

        String cachedSessionId = CACHE_CHAT_ROOM_PREFIX + sessionId;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(cachedSessionId))){
            // 이미 캐시된 데이터가 있을 경우
            return redisTemplate.opsForList().range(cachedSessionId, 0, -1);

        }else{
            // 영속성 DB에서 쿼리, 캐싱 후 데이터 가져오기
            List<ChatMessage> messages = chatMessageRepository.findAllBySessionIdOrderByTimestamp(sessionId);

            if(!messages.isEmpty()){
                cacheChatRoomMessages(messages, cachedSessionId);
                return messages;
            }
            // 새로운 ChatRoom일 경우 (영속성 데이터 없음)
            return null;

        }

    }

    public void cacheChatRoomMessages(List<ChatMessage> messages, String cachedSessionId) {
        ListOperations<String, ChatMessage> ops = redisTemplate.opsForList();
        for (ChatMessage message : messages) {
            ops.rightPush(cachedSessionId, message);
        }
        redisTemplate.expire(cachedSessionId, 30, TimeUnit.MINUTES);
    }

    public void sendMessage(String sessionId, ChatMessage message){

        ListOperations<String, ChatMessage> ops = redisTemplate.opsForList();
        String cachedSessionId = CACHE_CHAT_ROOM_PREFIX + sessionId;
        String newMessageSessionId = CACHE_NEW_MESSAGE_PREFIX + sessionId;

        // chatRoom별 캐시 데이터에 추가
        ops.rightPush(cachedSessionId, message);
        redisTemplate.expire(cachedSessionId, 30, TimeUnit.MINUTES);

        // 영속화 할 신규 메시지 추가
        ops.rightPush(newMessageSessionId, message);

        String destinationUrl = "/room/ + sessionId + /chat";

        messagingTemplate.convertAndSend(destinationUrl, message);

    }

    public String getChatRoomSessionId(Long sellerId, Long buyerId, Long auctionItemsId){

        ChatRoomSession session = chatRoomSessionRepository.findBySellerIdAndBuyerIdAndAuctionItemsId(sellerId, buyerId, auctionItemsId);
        if (session == null){
            String sessionId = UUID.randomUUID().toString();
            chatRoomSessionRepository.save(ChatRoomSession.builder()
                    .sessionId(sessionId)
                    .auctionItemsId(auctionItemsId)
                    .sellerId(sellerId)
                    .buyerId(buyerId).build());
            return sessionId;
        }
        return session.getSessionId();
    }

    @PostConstruct
    public void init() {
        executorService.scheduleAtFixedRate(() -> {
            // redis에 캐싱된 모든 새로운 메시지
            Set<String> sessionIds = redisTemplate.keys(CACHE_NEW_MESSAGE_PREFIX + "*");
            if (sessionIds != null){
                for (String sessionId : sessionIds) {
                    // sessionId에 캐싱된 모든 메시지
                    List<ChatMessage> messages = redisTemplate.opsForList().range(sessionId, 0, -1);
                    if (messages != null){
                        chatMessageRepository.saveAll(messages);
                        redisTemplate.delete(sessionId);
                    }
                }
            }

        }, batchTime, batchTime, TimeUnit.SECONDS);
    }

}

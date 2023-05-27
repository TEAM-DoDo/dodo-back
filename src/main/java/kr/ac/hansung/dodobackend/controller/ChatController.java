package kr.ac.hansung.dodobackend.controller;

import kr.ac.hansung.dodobackend.dto.ChatDTO;
import kr.ac.hansung.dodobackend.entity.Chat;
import kr.ac.hansung.dodobackend.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepository chatRepository;
    @MessageMapping("/{room_id}")
    public void sendChat(@DestinationVariable("room_id") int roomId, ChatDTO chat){
        System.out.println("===================== 받은 메세지 =====================");
        chat.setDate(Long.toString(Instant.now().getEpochSecond()));
        Chat c = Chat.builder().content(chat.getContent()).date(chat.getDate()).userId(chat.getUserId()).doId(roomId).build();
        Chat result = chatRepository.save(c);
        chat.setId(result.getId());
        System.out.println(chat);
        simpMessagingTemplate.convertAndSend("/topic/room/" + roomId,chat);
        //return message;
    }
    @SubscribeMapping("/{room_id}/enter")
    public List<Chat> sendChat(@DestinationVariable("room_id") int roomId){
        System.out.println("유저 방 입장 : Room id = "+ roomId);
        List<Chat> result = chatRepository.findChatsByDoId(roomId);
        Collections.reverse(result);
        return result;
    }
}


package kr.ac.hansung.dodobackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/{room_id}")
    public void sendChat(@PathVariable("room_id") int roomId, String message) throws Exception{
        System.out.println("===================== 받은 메세지 =====================");
//        System.out.println(message);
//        simpMessagingTemplate.convertAndSend("/topic/room/"+roomId,message);
        //return message;
    }
}


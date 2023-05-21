package kr.ac.hansung.dodobackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.hansung.dodobackend.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class ChatSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private ArrayList<WebSocketSession> participants = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        this.participants.add(session);
        System.out.println("채팅 접속자 수 : " + participants.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        this.participants.remove(session);
        System.out.println("채팅 접속자 수 : " + participants.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message.getPayload());
        System.out.println();
        String payload = message.getPayload();
        Chat chatMessage = objectMapper.readValue(payload, Chat.class);
        chatMessage.setTime((int)(new Date()).getTime());
        sendMessageToAll(chatMessage);
    }
    private void sendMessageToAll(Chat message){
        ArrayList<WebSocketSession> removeTargets = new ArrayList<>();
        participants.forEach(session -> {
            try{
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            } catch (Exception e) {
                System.out.println("Failed to send message, remove user from chat");
                removeTargets.add(session);
            }
        });
        participants.removeAll(removeTargets);
    }
}

package kr.ac.hansung.dodobackend;

import kr.ac.hansung.dodobackend.service.ChatSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//@RequiredArgsConstructor
//@Configuration
//@EnableWebSocket
//public class WebSockConfig implements WebSocketConfigurer , WebSocketMessageBrokerConfigurer {
//    private final ChatSocketHandler chatSocketHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(chatSocketHandler, "/api/chat").setAllowedOrigins("*/*");
//    }
//}
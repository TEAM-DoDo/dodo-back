package kr.ac.hansung.dodobackend.config;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.hansung.dodobackend.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class ChatUserInterceptor implements ChannelInterceptor {
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            String token = resolveToken(authHeader);
            if (token == null) {
                System.out.println("Chat enter filter : 토큰이 없습니다");
                throw new IllegalArgumentException("토큰이 없습니다.");
            }
            if (!jwtTokenProvider.validateToken(token)) {
                System.out.println("Chat enter filter : 토큰이 유효하지 않습니다");
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }
        return ChannelInterceptor.super.preSend(message, channel);
    }
    private String resolveToken(String bearerToken) {
        //System.out.println("token : " +bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            try {
                return bearerToken.substring(7);
            } catch (StringIndexOutOfBoundsException e){
                return null;
            }
        }
        return null;
    }
}

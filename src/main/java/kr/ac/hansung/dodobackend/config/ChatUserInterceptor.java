package kr.ac.hansung.dodobackend.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class ChatUserInterceptor implements ChannelInterceptor {
    //연결시 토큰 검증을 위한 코드 작성을 위한 핸들러
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        
        ChannelInterceptor.super.afterReceiveCompletion(message, channel, ex);
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        
        return ChannelInterceptor.super.preSend(message, channel);
    }
}

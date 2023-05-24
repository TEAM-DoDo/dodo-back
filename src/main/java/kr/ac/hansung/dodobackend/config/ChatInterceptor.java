package kr.ac.hansung.dodobackend.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class ChatInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        System.out.println(channel.toString());
        System.out.println(message.getPayload());
        return message;
    }
}

package com.stomp.custom.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * @createTime 2022年08月09日 21:16:00
 *
 * MessageChannel到客户端的ClientOutBoundChannel拦截器
 */
public class ClientOutBoundChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        return message;
    }
}

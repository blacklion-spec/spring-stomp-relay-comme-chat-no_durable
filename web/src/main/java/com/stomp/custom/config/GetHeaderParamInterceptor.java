package com.stomp.custom.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * spring应用作为消息代理中继的角色是这样的：客户端是websocket客户端，代理是rabbitmq，spring应用作为代理中继
 * 与rabbitmq通过brockChannel维持着TCP连接，称为系统连接；spring应用与websocket客户端通过ClientInboundChannel与ClientOutboundChannel
 * 维持一个TCP连接，分别处理客户端到服务器消息与服务器到客户端消息，ClientInboundChannel处理客户端到服务器消息，并最终到达MessageChannel处理
 * ClientInboundChannel处理服务器到客户端消息，由MessageChannel处理后发送。
 *
 * 所以ChannelInterceptor在ClientInboundChannel与ClientOutboundChannel拦截的逻辑不同，拦截器处在客户端与MessageChannel之间
 *
 * 注意：通过此MessageChannel处理发送的消息是已经完成websocket协议握手以后的。
 * HandshakeHandler是处理websocket握手处理器，HandshakeInterceptor是在握手之前与握手之后可以切入逻辑的处理器
 * 完成握手之后，消息才会在ClientOutboundChannel与ClientInboundChannel上流动，ChannelInterceptor才能切入逻辑
 *
 * 自己实验例子；
 * 握手之前 ---HandshakeInterceptor beforeHandshake（）
 * 握手之后 -- HandshakeInterceptor afterHandshake（）
 * ChannelInterceptor： InBound pre send：Thread[http-nio-8085-exec-2,5,main]
 * InBound pre send：GenericMessage [payload=byte[0], headers={simpMessageType=CONNECT, stompCommand=CONNECT, nativeHeaders={username=[123627], accept-version=[1.1,1.0], heart-beat=[25000,25000]}, simpSessionAttributes={}, simpHeartbeat=[J@1a8024cc, simpSessionId=5f60980b-88d0-2a3a-357e-eb394efe22ba}]
 * ChannelInterceptor：OutBoundChannel preSend：Thread[tcp-client-scheduler-2,5,main]
 * OutBoundChannel preSend：GenericMessage [payload=byte[0], headers={simpMessageType=OTHER, stompCommand=CONNECTED, nativeHeaders={server=[RabbitMQ/3.10.7], session=[session-ND830Hsf6ZCpZGrd8yE2eQ], heart-beat=[25000,25000], version=[1.1]}, simpUser=com.stomp.custom.config.UserPrincipal@61b39509, simpSessionId=5f60980b-88d0-2a3a-357e-eb394efe22ba}]
 */
@Component
public class GetHeaderParamInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null) {
            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
                if (raw instanceof Map) {
                    List name = (List)((Map) raw).get("username"); //取出客户端携带的参数
                    // 设置当前访问的认证用户
                    accessor.setUser(new UserPrincipal(name.get(0).toString()));
                }
            }
        }
        return message;
    }
}
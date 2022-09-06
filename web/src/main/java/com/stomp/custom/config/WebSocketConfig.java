package com.stomp.custom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

/**
 * @Author : JCccc
 * @CreateTime : 2020/8/26
 * @Description :
 **/

@Configuration
@EnableWebSocketMessageBroker //该注解的意思是启用websocket子协议代理
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    GetHeaderParamInterceptor getHeaderParamInterceptor;

    /**
     * 此处可以增加两个重要的接口 HandshakeInterceptor HandshakeHandler
     *
     * HandshakeHandler是处理websocket协议握手
     * HandshakeInterceptor 是在握手之前与握手以后增加逻辑切入点
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*");
        //暴露多个节点,就一样直接addEndpoint 就可以
//        registry.addEndpoint("/alone") .setAllowedOrigins("*")
//               .withSockJS();
    }

    /**
     * Spring Boot与Rabbit MQ 建立基于TCP协议之上的STMOP协议
     * <p>
     * 网页客户端还是与Spring BOOT 建立连接
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //如果不使用rabbitmq作为消息代理,那么只需要暴露简单节点即可
        // 默认的Simple Broker
        //   registry.enableSimpleBroker("/topic","/user");
        //  registry.setApplicationDestinationPrefixes("/app");
        // 使用RabbitMQ做为消息代理，替换默认的Simple Broker
        //定义了服务端接收地址的前缀，也即客户端给服务端发消息的地址前缀,@SendTo(XXX) 也可以重定向
        registry.setUserDestinationPrefix("/user"); //这是给sendToUser使用,前端订阅需要加上/user
        registry.setApplicationDestinationPrefixes("/app"); //这是给客户端推送消息到服务器使用 ，推送的接口加上/app
        // "STOMP broker relay"处理所有消息将消息发送到外部的消息代理
        registry.enableStompBrokerRelay("/topic", "/queue")
                .setVirtualHost("/") //对应自己rabbitmq里的虚拟host
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest") //客户端连接到此应用的用户名 可以缺省吧？(目前不知到这两个参数的作用)
                .setClientPasscode("guest") //客户端连接到此应用的密码
                .setSystemLogin("guest") //rabbit mq的用户名
                .setSystemPasscode("guest") //rabbit mq的密码
                .setSystemHeartbeatSendInterval(25000) //spring应用到rabbitmq的心跳间隔，不是与websocket客户端
                .setSystemHeartbeatReceiveInterval(25000)
        ;


    }

    /**
     * 客户端向服务器发送消息拦截器，最终到达MessageChannel之前与之后增加逻辑切入点
     * 这里的getHeaderParamInterceptor是获取到stomp协议的CONNECT帧中的自定义username头
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(getHeaderParamInterceptor);
    }

    /**
     * api：https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/messaging/support/ChannelInterceptor.html
     * 从服务器到客户端的MessageChannel拦截器，在到达MessageChannel之前与之后可以增加逻辑切入点
     * @param registration
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ClientOutBoundChannelInterceptor());
    }

    //配置消息转换器，以便在以带注释的方法(MessageMapping注释的方法)提取消息有效负载和发送消息时使用
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        messageConverters.add(new MappingJackson2MessageConverter());
        return false;
    }

}
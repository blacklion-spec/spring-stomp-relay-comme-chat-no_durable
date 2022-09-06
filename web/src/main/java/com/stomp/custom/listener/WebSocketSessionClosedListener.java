package com.stomp.custom.listener;

import com.stomp.custom.mode.ChatMessage;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

import static com.stomp.custom.constant.Destination.userState;

/**
 * @createTime 2022年08月09日 14:43:00
 * <p>
 * 监听stomp协议断开事件
 */
@Component
public class WebSocketSessionClosedListener implements ApplicationListener<SessionDisconnectEvent> {

    private final SimpMessagingTemplate template;

    public WebSocketSessionClosedListener(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        Principal user = sessionDisconnectEvent.getUser();
        if (user != null) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSender(user.getName());
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            template.convertAndSend(userState, chatMessage);
        }
    }
}

package com.stomp.custom.constant;

/**
 * @createTime 2022年08月13日 10:47:00
 *
 * stomp Destination
 */
public interface Destination {

    /**
     * 广播消息的Destination
     */
    String sendMessage = "/topic/chat.message";

    /**
     * 广播上线，下线消息的Destination
     */
    String userState = "/topic/chat.state";

}

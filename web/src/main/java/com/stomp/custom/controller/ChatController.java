package com.stomp.custom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @createTime 2022年09月02日 15:33:00
 */
@Controller
public class ChatController {

    public static final String CHAT_INDEX_MAPPING = "/chat/index";
    public static final String CHAT_HTML = "chat";

    @GetMapping(CHAT_INDEX_MAPPING)
    public String chatIndex() {
        return CHAT_HTML;
    }

}

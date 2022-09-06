package com.stomp.custom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @createTime 2022年08月09日 22:11:00
 * 页面跳转处理器
 */
@Controller
public class IndexController {

    public final static String INDEX_MAPPING = "/";
    public final static String INDEX_HTML = "index";

    @GetMapping(INDEX_MAPPING)
    public String index() {
        return INDEX_HTML;
    }

}

package com.stomp.custom.controller;

import com.stomp.custom.dto.RegisterDto;
import com.stomp.custom.service.RegisterService;
import com.stomp.custom.vo.Response;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @createTime 2022年08月21日 15:31:00
 */
@Controller
public class RegisterController {

    public static final String REGISTER_INDEX_MAPPING = "/register/index";
    public static final String REGISTER_HTML = "registered";
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping(REGISTER_INDEX_MAPPING)
    public String index() {
        return REGISTER_HTML;
    }

    @PostMapping
    @ResponseBody
    public Response doRegister(@ModelAttribute @Validated RegisterDto registerDto, HttpServletResponse response) {
        return registerService.doRegister(registerDto, response);
    }


}

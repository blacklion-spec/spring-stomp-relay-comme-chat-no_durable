package com.stomp.custom.controller;

import com.stomp.custom.service.IMailHandleService;
import com.stomp.custom.vo.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @createTime 2022年08月21日 10:23:00
 */
@Controller
@RequestMapping("/mail")
public class MailController {

    public static final String SEND_VERIFICATION_CODE = "/sendVerificationCode";
    private final IMailHandleService mailService;

    public MailController(IMailHandleService mailService) {
        this.mailService = mailService;
    }


    //发送验证码
    @PostMapping(SEND_VERIFICATION_CODE)
    @ResponseBody
    public Response sendVerCodeForMail(@RequestBody Map<String,String> param, HttpServletResponse response) {
        return mailService.sendVerCodeForMail(param.get("mail"), response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response exception(Exception e,HttpServletResponse response) {
        response.setStatus(500);
        return new Response().setSuccess(false).setMsg(e.getMessage());
    }


}

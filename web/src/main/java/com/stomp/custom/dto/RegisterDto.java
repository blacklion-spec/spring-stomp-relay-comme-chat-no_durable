package com.stomp.custom.dto;

import com.stomp.custom.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @createTime 2022年08月21日 22:00:00
 */
@Data
public class RegisterDto {

    //用户邮箱
    @NotBlank(message = "邮箱不能为空")
    private String userMail;

    //验证码
    @NotBlank(message = "验证码不能为空")
    private String verificationCode;

    //用户名
    @NotBlank(message = "用户名不能为空")
    private String username;

    //密码
    @NotBlank(message = "密码不能为空")
    @Size(message = "密码长度为16", min = 10, max = 16)
    private String password;

    public User toUser() {
        User user = new User();
        user.setUserMail(this.userMail);
        user.setUserName(this.username);
        user.setPassword(this.password);
        user.setCreateTime(new Date());
        return user;
    }
}

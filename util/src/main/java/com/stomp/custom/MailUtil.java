package com.stomp.custom;


import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @createTime 2022年08月19日 22:24:00
 */
public class MailUtil {

    private static final String MAIL_EXPRESSION = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$";
    private static final Pattern MAIL_VALIDATOR = Pattern.compile(MAIL_EXPRESSION);

    //验证其是否为合法的邮箱
    public static boolean validationMail(String mail) {
        if (StringUtils.isEmpty(mail)) {
            return false;
        }
        Matcher matcher = MAIL_VALIDATOR.matcher(mail);
        return matcher.find();
    }


}

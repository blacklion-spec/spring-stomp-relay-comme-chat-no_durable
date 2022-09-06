package com.stomp.custom;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @createTime 2022年08月21日 14:53:00
 */
//验证码工具类
public class VerifiUtil {

    //生成验证码
    public static String createVerifCode() {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        return current.nextInt(1000, 9999) + "";
    }

}

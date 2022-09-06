package com.stomp.custom.mail;

import com.stomp.custom.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 读写分离测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DbTest {

    /**
     * 注入发送邮件的接口
     */
   /* @Autowired
    private AccessFactory accessFactory;


    @Test
    public void readSlave() {
        *//*User user = new User();
        user.setPassword("22");
        user.setCreateTime(new Date());
        user.setUserName("xx");
        user.setIsBan((byte) 1);
        user.setUserMail("12345");
        boolean insert = mapper.insert(user);
        System.out.println(insert);*//*
        User xx = accessFactory.createUserAccess().getByUsername("xx");
        System.out.println(xx);
    }*/

}


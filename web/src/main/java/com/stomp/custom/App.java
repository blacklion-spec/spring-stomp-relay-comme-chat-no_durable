package com.stomp.custom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @createTime 2022年08月06日 10:08:00
 */
@SpringBootApplication(scanBasePackages = {"com.stomp.custom"})
@MapperScan("com.stomp.custom.mapper")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}

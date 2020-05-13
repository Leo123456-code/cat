package com.stylefeng.user;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//开启dubbo
@EnableDubboConfiguration
public class GunsFileApplication {

    public static void main(String[] args) {

        SpringApplication.run(GunsFileApplication.class, args);
    }

}

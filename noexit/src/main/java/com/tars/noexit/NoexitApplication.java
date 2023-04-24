package com.tars.noexit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.tars.noexit.mapper")
@EnableFeignClients
public class NoexitApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoexitApplication.class, args);
    }

}

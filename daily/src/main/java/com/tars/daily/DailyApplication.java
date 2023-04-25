package com.tars.daily;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.tars.daily.mapper")
@EnableFeignClients
public class DailyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyApplication.class, args);
    }

}

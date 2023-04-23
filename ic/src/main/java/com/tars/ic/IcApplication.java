package com.tars.ic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.tars.ic.mapper")
@EnableFeignClients
public class IcApplication {

    public static void main(String[] args) {
        SpringApplication.run(IcApplication.class, args);
    }


}

package com.tars.noexit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tars.noexit.mapper")
public class NoexitApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoexitApplication.class, args);
    }

}

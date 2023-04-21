package com.tars.ie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
@MapperScan("com.tars.ie.mapper")
public class IeApplication {

    public static void main(String[] args) {
        SpringApplication.run(IeApplication.class, args);
    }

}

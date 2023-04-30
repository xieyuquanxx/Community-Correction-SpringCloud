package com.tars.ie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.tars.ie.mapper")
@EnableDiscoveryClient
public class IeApplication {

    public static void main(String[] args) {
        SpringApplication.run(IeApplication.class, args);
    }

}

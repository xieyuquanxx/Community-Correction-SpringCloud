package com.tars.ic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.tars.ic.mapper")
public class IcApplication {

    public static void main(String[] args) {
        SpringApplication.run(IcApplication.class, args);
    }

    @Bean
    @LoadBalanced//表示用负载均衡调用服务
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }



}

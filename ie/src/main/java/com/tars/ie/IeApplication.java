package com.tars.ie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.tars.ie.mapper")
@EnableFeignClients
public class IeApplication {

  public static void main(String[] args) {
    SpringApplication.run(IeApplication.class, args);
  }

}

package com.tars.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OssApplication {

  public static void main(String[] args) {
    SpringApplication.run(OssApplication.class, args);
  }

}

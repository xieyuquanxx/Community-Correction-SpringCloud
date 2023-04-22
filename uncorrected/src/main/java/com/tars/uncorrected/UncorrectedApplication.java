package com.tars.uncorrected;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tars.uncorrected.mapper")
public class UncorrectedApplication {

    public static void main(String[] args) {
        SpringApplication.run(UncorrectedApplication.class, args);
    }

}

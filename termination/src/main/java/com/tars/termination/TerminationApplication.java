package com.tars.termination;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tars.termination.mapper")
public class TerminationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TerminationApplication.class, args);
    }

}

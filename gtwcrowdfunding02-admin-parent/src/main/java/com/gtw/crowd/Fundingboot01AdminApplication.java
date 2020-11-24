package com.gtw.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gtw.crowd.mapper")
public class Fundingboot01AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(Fundingboot01AdminApplication.class, args);
    }

}

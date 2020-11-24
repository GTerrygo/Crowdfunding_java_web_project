package com.gtw.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author
 * @create 2020-11-15-19:51
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.gtw.crowd.mapper")
public class CrowdMysqlMain {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMysqlMain.class,args);
    }
}

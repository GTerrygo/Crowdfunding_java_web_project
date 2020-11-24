package com.gtw.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author
 * @create 2020-11-15-22:19
 */
@SpringBootApplication
@EnableEurekaClient
public class CrowdRedisMain {
    public static void main(String[] args) {
        SpringApplication.run(CrowdRedisMain.class,args);
    }
}

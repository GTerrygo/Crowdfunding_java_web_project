package com.gtw.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author
 * @create 2020-11-15-2:43
 */
@SpringBootApplication
@EnableEurekaServer
public class CrowdEurekaMain {
    public static void main(String[] args) {
        SpringApplication.run(CrowdEurekaMain.class,args);
    }
}

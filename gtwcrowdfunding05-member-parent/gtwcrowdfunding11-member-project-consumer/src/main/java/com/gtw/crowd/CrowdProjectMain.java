package com.gtw.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author
 * @create 2020-11-19-0:57
 */
@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
@EnableRedisHttpSession
public class CrowdProjectMain {
    public static void main(String[] args) {
        SpringApplication.run(CrowdProjectMain.class,args);
    }
}

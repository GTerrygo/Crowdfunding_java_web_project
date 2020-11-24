package com.gtw.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

/**
 * @author
 * @create 2020-11-16-2:06
 */
@SpringBootApplication
@EnableRedisWebSession
@EnableEurekaClient
public class CrowdGatewayMain {
    public static void main(String[] args) {
        SpringApplication.run(CrowdGatewayMain.class,args);
    }
}

package com.gtw.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author
 * @create 2020-11-15-23:31
 */
@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
@EnableRedisHttpSession
public class CrowdAuthenticationMain {
    public static void main(String[] args) {
        SpringApplication.run(CrowdAuthenticationMain.class,args);
    }
}

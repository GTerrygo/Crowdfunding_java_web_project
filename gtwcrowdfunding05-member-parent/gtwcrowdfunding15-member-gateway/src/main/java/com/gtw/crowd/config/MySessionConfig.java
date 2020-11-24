package com.gtw.crowd.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

/**
 * @author
 * @create 2020-11-18-3:28
 */
@Configuration
public class MySessionConfig {

    @Bean
    public WebSessionIdResolver webSessionIdResolver(){
        CookieWebSessionIdResolver resolver = new CookieWebSessionIdResolver();
        resolver.setCookieName("SESSION");
        resolver.addCookieInitializer(initializer -> initializer.domain("localhost"));
        resolver.addCookieInitializer(initializer -> initializer.domain("/"));
        return resolver;
    }

    @Bean
    @Qualifier("springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(){
        return  new GenericJackson2JsonRedisSerializer();
    }

//    private ObjectMapper objectMapper(){
//        ObjectMapper objectMapper=new ObjectMapper();
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        return  objectMapper;
//    }
}

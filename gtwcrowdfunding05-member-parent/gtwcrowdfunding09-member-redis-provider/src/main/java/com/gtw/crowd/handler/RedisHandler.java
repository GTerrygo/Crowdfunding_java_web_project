package com.gtw.crowd.handler;

import com.gtw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author
 * @create 2020-11-15-23:12
 */
@RestController
public class RedisHandler {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity setRedisKeyValueRemote(@RequestParam("key") String key,
                                        @RequestParam("value") String value) {
        try {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set(key, value);
            return ResultEntity.success();
        } catch (Exception exception) {
            return ResultEntity.error(exception.getMessage());
        }
    }

    @RequestMapping(value = "/set/redis/key/value/timeout/remote",method = RequestMethod.POST)
    ResultEntity setRedisKeyValueRemoteWithTimeout(@RequestParam("key") String key,
                                                   @RequestParam("value") String value,
                                                   @RequestParam("time") long time,
                                                   @RequestParam("timeUnit") TimeUnit timeUnit){

        try {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set(key, value,time,timeUnit);
            return ResultEntity.success();
        } catch (Exception exception) {
            return ResultEntity.error(exception.getMessage());
        }
    }


    @RequestMapping("/get/redis/value/by/key/remote")
    ResultEntity getRedisValueByKeyRemote(@RequestParam("key") String key){
        try {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            String value = ops.get(key);
            return ResultEntity.success().add("value",value);
        } catch (Exception exception) {
            return ResultEntity.error(exception.getMessage());
        }
    }

    @RequestMapping("/remote/redis/value/by/key/remote")
    ResultEntity remoteRedisValueByKeyRemote(@RequestParam("key") String key){
        try {
            redisTemplate.delete(key);
            return ResultEntity.success();
        } catch (Exception exception) {
            return ResultEntity.error(exception.getMessage());
        }
    }
}

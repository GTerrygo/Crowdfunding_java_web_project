package com.gtw.crowd.api;

import com.gtw.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * @author
 * @create 2020-11-15-22:42
 */
@FeignClient("gtw-crowd-redis")
@Component
public interface RedisRemoteService {

    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity setRedisKeyValueRemote(@RequestParam("key") String key,
                                        @RequestParam("value") String value);

    @RequestMapping(value = "/set/redis/key/value/timeout/remote",method = RequestMethod.POST)
    ResultEntity setRedisKeyValueRemoteWithTimeout(@RequestParam("key") String key,
                                                   @RequestParam("value") String value,
                                                   @RequestParam("time") long time,
                                                   @RequestParam("timeUnit")TimeUnit timeUnit);
    @RequestMapping("/get/redis/value/by/key/remote")
    ResultEntity getRedisValueByKeyRemote(@RequestParam("key") String key);

    @RequestMapping("/remote/redis/value/by/key/remote")
    ResultEntity remoteRedisValueByKeyRemote(@RequestParam("key") String key);
}

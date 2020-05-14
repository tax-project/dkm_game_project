package com.dkm.shop.fegin;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @program: springcloudhbats
 * @description
 * @author: zy
 * @create: 2020-01-21 14:04
 **/
@FeignClient(name="redisService",fallback =RedisErrorBack.class )
public interface RedisService {


}
@Component
class RedisErrorBack implements RedisService{

}
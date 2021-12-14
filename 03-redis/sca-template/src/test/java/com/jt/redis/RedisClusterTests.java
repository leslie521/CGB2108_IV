package com.jt.redis;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisClusterTests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void testMasterReadWrite(){
        //1.获取数据操作对象
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //2.读写数据
        valueOperations.set("city","beijing");
        Object city=valueOperations.get("city");
        System.out.println(city);
    }
}

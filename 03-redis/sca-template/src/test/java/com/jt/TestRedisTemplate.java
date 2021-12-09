package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class TestRedisTemplate {

    /**
     * RedisTemplate 是spring工程中提供的一个用于操作Redis数据库的API
     * 此对象采用了模板方法模式，对操作Redis的步骤进行了相关的封装，定义一些
     * 具体方法，后续就可以基于这些方法实现对Redis数据库的读写操作了
     */
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testGetConnection(){
        RedisConnection connection = redisTemplate.getRequiredConnectionFactory().getConnection();
        String ping = connection.ping();
        System.out.println(ping);
    }
}

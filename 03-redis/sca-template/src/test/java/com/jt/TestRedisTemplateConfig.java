package com.jt;

import com.jt.redis.config.RedisConfig;
import com.jt.redis.pojo.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

@SpringBootTest
public class TestRedisTemplateConfig {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void testRedisConfig(){

        Blog blog = new Blog(101,"blg");
        ValueOperations vo = redisTemplate.opsForValue();
        vo.set("blog", blog);
        blog =(Blog) vo.get("blog");
        System.out.println(blog);
    }
}
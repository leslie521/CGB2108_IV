package com.jt;

import com.jt.redis.pojo.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.Map;

@SpringBootTest
public class TestRedisTemplate {

    /**
     * RedisTemplate 是spring工程中提供的一个用于操作Redis数据库的API
     * 此对象采用了模板方法模式，对操作Redis的步骤进行了相关的封装，定义一些
     * 具体方法，后续就可以基于这些方法实现对Redis数据库的读写操作了
     */
    @Autowired
    private RedisTemplate redisTemplate;//springboot工程启动时创建

    @Test
    void testHashBlog(){
        ValueOperations ho = redisTemplate.opsForValue();
        Blog blog = new Blog();
        blog.setId(100);
        blog.setName("andy");
        ho.set("blog", blog);
        blog =(Blog)ho.get("blog");
        System.out.println(blog);
    }

    @Test
    void testHashOper(){
        HashOperations ho = redisTemplate.opsForHash();
        ho.put("blog", "id", 100);
        ho.put("blog", "name", "tony");
//        ho.putAll();
        Object name = ho.get("blog", "name");
        System.out.println(name);
        Map blog = ho.entries("blog");
        System.out.println(blog);
    }

    @Test
    void testStringOper(){
//        redisTemplate.setKeySerializer(RedisSerializer.string());
//        redisTemplate.setValueSerializer(RedisSerializer.string());
        ValueOperations<String,String> vo = redisTemplate.opsForValue();
//        vo.set("id", 100, Duration.ofSeconds(10));
        vo.set("id", "100");
        Object id = vo.get("id");
//        vo.increment("id");
        System.out.println(id);
    }

    @Test
    void testGetConnection(){
        RedisConnection connection = redisTemplate.getRequiredConnectionFactory().getConnection();
        String ping = connection.ping();
        System.out.println(ping);
    }
}

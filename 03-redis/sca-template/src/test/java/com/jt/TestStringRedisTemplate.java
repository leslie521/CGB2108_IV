package com.jt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.redis.pojo.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
public class TestStringRedisTemplate {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testBlogJson() {

        Blog blog = new Blog(101,"blg");
        stringRedisTemplate.setValueSerializer(RedisSerializer.json());
        ValueOperations vo = stringRedisTemplate.opsForValue();
        vo.set("blog", blog);
        blog =(Blog) vo.get("blog");
        System.out.println(blog);
//        System.out.println(name);
    }

    @Test
    void testHashOperator(){
        HashOperations<String, Object, Object> vo = stringRedisTemplate.opsForHash();
        vo.put("zb", "x", "10");
        vo.put("zb", "y", "100");
        vo.put("zb", "z", "1000");
        vo.put("zb", "z", "7000");
        Boolean aBoolean = vo.hasKey("zb", "z");
        System.out.println(aBoolean);
        Map<Object, Object> zbMap = vo.entries("zb");
        System.out.println(zbMap);
        Set<Object> keys = vo.keys("zb");
        List<Object> values = vo.values("zb");
        System.out.println(keys+"\n"+values);
    }

    @Test
    void testStringTemplateOper1(){
        ValueOperations<String, String> svo = stringRedisTemplate.opsForValue();
        svo.set("id", "100");
        svo.set("name", "sally");
        svo.set("age", "60");
        Long id = svo.increment("id");
        String name = svo.get("name");
        System.out.println(id);
        System.out.println(name);
    }
}

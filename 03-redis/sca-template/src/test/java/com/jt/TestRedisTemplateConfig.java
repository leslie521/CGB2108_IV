package com.jt;


import com.jt.redis.pojo.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class TestRedisTemplateConfig {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将blog对象以json串的方式写到redis数据库，
     * 并将其读出了进行输出
     */
    @Test
    void testRedisConfig(){

        Blog blog = new Blog();
        blog.setId(100);
        blog.setName("hello world");
        ValueOperations vo = redisTemplate.opsForValue();
        vo.set("blog", blog);
        blog =(Blog) vo.get("blog");
        System.out.println(blog);
    }
}

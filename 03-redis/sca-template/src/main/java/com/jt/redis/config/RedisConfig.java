package com.jt.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object,Object> redisTemplate(
            RedisConnectionFactory connectionFactory){
        //1.构建Redistemplate对象
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        //2.修改key/value的序列化方式
        //设置大key、小key的序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        //设置值的序列化方式
//        redisTemplate.setValueSerializer(RedisSerializer.json());
//        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        //更新Redistemplate对象配置
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}

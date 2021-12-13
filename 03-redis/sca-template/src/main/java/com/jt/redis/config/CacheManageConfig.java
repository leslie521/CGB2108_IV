package com.jt.redis.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * 这是一个缓存配置类
 */
@Configuration
public class CacheManageConfig {

    /**
     * 基于业务需要，例如改变原有默认的redis序列化方式，
     * 这里需要修改原有默认CacheManager对象的配置。
     * @return
     */
    @Bean
    public CacheManager cacheManage(RedisConnectionFactory connectionFactory){
        //构建RedisCacheConfig对象
        RedisCacheConfiguration cacheConfig =
                RedisCacheConfiguration.defaultCacheConfig()
                //定义key的序列方式(默认key的序列化方式就是string)
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(RedisSerializer.string()))
//                .serializeValuesWith(RedisSerializationContext
//                        .SerializationPair.fromSerializer(RedisSerializer.string()));
                // 定义值的序列化方式
                .serializeValuesWith(RedisSerializationContext
                .SerializationPair.fromSerializer(RedisSerializer.json()))//jackson
                .entryTtl(Duration.ofSeconds(10));

        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }
}

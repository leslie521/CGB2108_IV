package com.jt.redis.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 在此类中定义RedisTemplate对象，
 * 修改默认的序列化方式。
 */
@Configuration
public class RedisConfig {

    /**
     * 自定义序列化规则，本次基于jackson api完成序列化和反序列化设计
     */
    @Bean
    public RedisSerializer<Object> jsonSerializer(){
        //1.构建RedisSerializer对象(构建对象时，指定可以为哪些对象序列化)
        Jackson2JsonRedisSerializer serializer=//本次序列化方案基于jackson api进行实现
                new Jackson2JsonRedisSerializer(Object.class);//Object.class表示可序列化类型
        //2.定义序列化和反序列化规则，这些规则需要通过ObjectMapper进行定义和封装。
        //2.1构建ObjectMapper对象
        ObjectMapper objectMapper=new ObjectMapper();
        //2.2设置对象序列化时，调用哪些方法获取key和value,来构建json串。
        objectMapper.setVisibility(PropertyAccessor.GETTER,//对象的get方法
                JsonAutoDetect.Visibility.ANY);//方法的访问修饰任意
        //2.3设置是否允许json串中key对应的值为null
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //2.4设置是否在序列化时，存储对象类型
        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),//默认的多态校验
                ObjectMapper.DefaultTyping.NON_FINAL,//非final类型，要将类型信息写入到json中
                JsonTypeInfo.As.PROPERTY);//类型信息以json属性形式存储到json中
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory){
        //1.构建RedisTemplate对象
        RedisTemplate redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        //2.修改key/value的序列化方式
        //设置大key，小key序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        //设置值的序列化方式
        //方案1：基于RedisSerializer对象中的json序列化方式实现值的序列化
        //redisTemplate.setValueSerializer(RedisSerializer.json());
        //redisTemplate.setHashValueSerializer(RedisSerializer.json());
        //方案2：自定义序列化方式(jackson,fastjson,gson 这些序列化方式自己选)
        redisTemplate.setValueSerializer(jsonSerializer());
        redisTemplate.setHashValueSerializer(jsonSerializer());

        //更新RedisTemplate对象的默认配置
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}

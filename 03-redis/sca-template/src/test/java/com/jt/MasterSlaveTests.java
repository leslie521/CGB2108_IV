package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class MasterSlaveTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testMasterReadWrite(){//配置文件端口为6379
        ValueOperations vo = redisTemplate.opsForValue();
        vo.set("ip", "172.17.0.4");
        vo.set("role", "master6379");
        Object role = vo.get("role");
        System.out.println(role);
    }

    @Test
    void testSlaveOnlyRead(){//配置文件端口为6380
        ValueOperations vo = redisTemplate.opsForValue();
        Object role = vo.get("role");
//        vo.set("1", "sh"); //错误
//        Object o = vo.get("1");
//        System.out.println(o);
        System.out.println(role);
    }

}

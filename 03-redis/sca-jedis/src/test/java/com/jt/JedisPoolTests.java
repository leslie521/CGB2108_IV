package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolTests {

    @Test
    public void testJedisPool01(){
//        JedisPool jedisPool = new JedisPool("192.168.126.128",6379);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1000);//设置最大连接数
        config.setMaxIdle(60);//设置最大空闲连接数
        JedisPool jedisPool = new JedisPool(config,"192.168.126.128",6379);
        Jedis resource = jedisPool.getResource();
        resource.set("poolName", "JedisPool");
        String poolName = resource.get("poolName");
        System.out.println(poolName);
        resource.close();
        jedisPool.close();
    }

    @Test
    public void testJedisPool02(){
        //1.从池中获取一个连接对象(Jedis)
        Jedis resource = JedisDataSource.getConnection();
        //2.基于Jedis读写redis数据
        resource.set("poolName", "JedisPool");
        String poolName = resource.get("poolName");
        System.out.println(poolName);
        //3.释放资源
        resource.close();
    }
}

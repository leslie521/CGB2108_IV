package com.jt;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class testJedisCluster {
    @Test
    void testJedisCluster()throws Exception{
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.126.129",8010));
        nodes.add(new HostAndPort("192.168.126.129",8011));
        nodes.add(new HostAndPort("192.168.126.129",8012));
        nodes.add(new HostAndPort("192.168.126.129",8013));
        nodes.add(new HostAndPort("192.168.126.129",8014));
        nodes.add(new HostAndPort("192.168.126.129",8015));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        //使用jedisCluster操作redis
        jedisCluster.set("test", "cluster");
        String str = jedisCluster.get("test");
        System.out.println(str);
        //关闭连接池
        jedisCluster.close();
    }

}

package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TestJedis {

    @Test
    public void testStringOper(){
        Jedis jedis = new Jedis("192.168.126.128", 6379);
        //2.1插入添加数据
        jedis.set("id", "100");
        jedis.set("name", "Andy");
        //2.2更新资源
        jedis.incr("id");
        jedis.incrBy("id",10);
        jedis.set("name", "Sally");
        String id = jedis.get("id");
        String name = jedis.get("name");
        System.out.printf("id=%s,name=%s \n",id,name);
        //2.4删除(delete)
        Long result = jedis.del("name");
        System.out.printf("result=%d",result);
        //关闭资源
        jedis.close();
    }

    @Test
    public void testGetConnection(){
        Jedis jedis = new Jedis("192.168.126.128", 6379);
//        jedis.auth("123456");
        String ping = jedis.ping();
        System.out.println(ping);
    }
}

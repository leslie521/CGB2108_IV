package com.jt;

import com.google.gson.Gson;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JedisTests {
    /*
    以hash类型方式，存储一个map结构数据
     */
    @Test
    public void testStringHash01(){
        Jedis jedis = new Jedis("192.168.126.128", 6379);
        String token = UUID.randomUUID().toString();
        Map<String,String> map = new HashMap<>();
        map.put("id", "10");
        map.put("name", "Lam");
        jedis.hset(token, map);
//        jedis.hset(token, "age", "10");
        jedis.expire(token, 10);
        String hashId = jedis.hget(token, "id");
        String hashName = jedis.hget(token, "name");
        Map<String,String> userMap = jedis.hgetAll(token);
        System.out.printf("hashId=%s \n",hashId);
        System.out.printf("hashName=%s \n",hashName);
        System.out.println(userMap);
        jedis.close();
    }
    /*
     将对象转换为json格式字符串
     然后存储到Redis中
     */
    @Test
    public void testStringOper2(){
        Jedis jedis = new Jedis("192.168.126.128", 6379);
        Map<String,String> userMap = new HashMap<>();//将来可以是POJO
        userMap.put("id", "100");
        userMap.put("name", "jack");
        Gson gson = new Gson();//Google提供的API
        String userJson = gson.toJson(userMap);//将对象转换为json格式字符串
        String token = UUID.randomUUID().toString();
        System.out.printf("token=%s \n",token);
        jedis.set(token,userJson);
        jedis.expire(token, 10);//设置key的有效期10秒钟
        userJson = jedis.get(token);
        System.out.println("userJson"+userJson);
        userMap = gson.fromJson(userJson, Map.class);
        System.out.println(userMap);
    }

    /*测试字符串的操作*/
    @Test
    public void testStringOper(){
        //1.建立连接
        Jedis jedis = new Jedis("192.168.126.128", 6379);
        //2.执行字符串操作
        //2.1插入添加数据(insert)
        jedis.set("id", "100");
        jedis.set("name", "Andy");
        //2.2更新资源(update)
        jedis.incr("id");
        jedis.incrBy("id",10);
        jedis.set("name", "Sally");
        //2.3查询数据(select)
        String id = jedis.get("id");
        String name = jedis.get("name");
        jedis.append("age", "18");
        String age = jedis.get("age");
        jedis.append("log", "set operator");
        String log = jedis.get("log");
        System.out.printf("id=%s,name=%s,age=%s,log=%s \n",id,name,age,log);
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

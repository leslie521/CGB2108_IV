package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.swing.*;

public class JedisTransactionTests {

    @Test
    public void testTransaction(){
        Jedis jedis = new Jedis("192.168.126.128", 6379);
//        jedis.auth("123456");
        jedis.set("tony", "300");
        jedis.set("jack", "500");
        //实现操作,tony转账100给jack
        //开启事务
        Transaction multi = jedis.multi();
        //执行业务操作
        try {
            multi.decrBy("tony", 100);
            multi.incrBy("jack", 100);
//            int n = 100/0;//模拟异常
            //提交事务
            multi.exec();
        }catch (Exception e){
            //出现异常取消事务
            multi.discard();
        }
        String tonyMoney = jedis.get("tony");
        String jackMoney = jedis.get("jack");
        System.out.println("tonyMoney"+tonyMoney);
        System.out.println("jackMoney"+jackMoney);
        jedis.close();
    }
}

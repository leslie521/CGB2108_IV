package com.jt.demos;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * redis秒杀练习:
 * 模拟两个线程都去抢购同一张票(考虑乐观锁)
 */
public class SecondKillDemo02 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.126.128", 6379);
//        jedis.auth("123456");
        jedis.set("ticket", "1");
        jedis.set("money", "0");

        Thread t1 = new Thread(() -> {
           secKill();
        });
        Thread t2 = new Thread(() -> {
            secKill();
        });
        t1.start();
        t2.start();
    }

    private static void secKill() {
        Jedis jedis = new Jedis("192.168.126.128", 6379);
//        jedis.auth("123456");
        jedis.watch("ticket","money");
        String ticket = jedis.get("ticket");
        if (ticket==null || Integer.valueOf(ticket)==0)
            throw new RuntimeException("已无库存");
        Transaction multi = jedis.multi();
        try {
            multi.decr("ticket");
            multi.incrBy("money", 100);
            List<Object> exec = multi.exec();
            System.out.println(exec);
        }catch (Exception e){
            e.printStackTrace();
            multi.discard();
        }finally {
            jedis.unwatch();
            jedis.close();
        }
    }
}

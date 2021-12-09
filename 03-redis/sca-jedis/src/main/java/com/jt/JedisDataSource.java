package com.jt;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisDataSource {

    private static final String HOST = "192.168.126.128";
    private static final int PORT = 6379;
    /*方案一：饿汉式设计(类加载时创建对象)*/
    /**
     * volatile 可以修饰属性
     * 1)可以禁止指令重排序(JVM为了优化指令的执行有可能会对指令进行排序)
     * 2)可以保证其可见性(一个线程修改了值，其它线程可见)
     * 3)不能保证其原子性(要么都执行，要么都不执行)
     */
    private static volatile JedisPool jedisPool;
//    static{
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(1000);
//        config.setMaxIdle(60);
//        jedisPool = new JedisPool(config,HOST, PORT);
//    }
//
//    public static Jedis getConnection(){
//        return jedisPool.getResource();
//    }

    /*方案二：懒汉式设计(何时应用何时创建池对象)*/
    public static Jedis getConnection(){
        if (jedisPool==null){
            synchronized(JedisDataSource.class){
                Object o = new Object();
                if (jedisPool==null){
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(1000);
                    config.setMaxIdle(60);
                    jedisPool = new JedisPool(config,HOST, PORT);
                    //分配内存
                    //初始化属性
                    //执行构造方法
                    //将对象赋值给引用变量
                }
            }
        }

        return jedisPool.getResource();
    }

    public static void close(){
        jedisPool.close();
    }

}

package com.jt.demos;

import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IdGeneratorDemo01 {

    /**
     * 需求:生成一个分布递增的id
     * 多张表基于这个方法中生成的id作为主键id值(分布式环境不会采用数据库
     * 表中自带的自增策略-auto_increment)
     */
    public static Long getId(){
        Jedis jedis = new Jedis("192.168.126.128", 6379);
//        jedis.auth("123456");//假如redis设置了密码,连接redis时需要指定密码
        Long id = jedis.incr("id");
        jedis.close();
        return id;
    }

    //自己创建线程执行任务
    static void execute01(){
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    String tName = Thread.currentThread().getName();
                    System.out.println(tName+"->"+IdGeneratorDemo01.getId());
//                    super.run();
                }
            }.start();
        }
    }

    //基于线程池执行任务
    static void execute02(){
        //构建一个最多只有3个线程的线程池
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 0; i <= 10; i++) {
            //从池中取线程执行任务
            pool.execute(new Runnable() {//这个任务会存储到阻塞式任务队列中
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"->"+getId());
                }
            });
        }
    }

    public static void main(String[] args) {
//        execute01();
        execute02();
    }

}

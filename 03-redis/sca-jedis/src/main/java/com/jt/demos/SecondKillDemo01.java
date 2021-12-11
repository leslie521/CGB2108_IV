package com.jt.demos;

import redis.clients.jedis.Jedis;

//秒杀队列演示
//描述逻辑中会将商品抢购信息先写到redis(以队列形式进行存储),
//因为写redis内存数据库要比写你的mysql数据库快很多倍
//算法:先进先出(FIFO)-体现公平性
public class SecondKillDemo01 {

    //商品抢购首先是入队
    static void enqueue(String msg){//入队
        Jedis jedis = new Jedis("192.168.126.128", 6379);
//        jedis.auth("123456");
        jedis.lpush("queue", msg);
        jedis.close();
    }

    //底层异步出队(基于这个消息,生成订单,扣减库存,...)
    static String dequeue(){
        Jedis jedis = new Jedis("192.168.126.128", 6379);
//        jedis.auth("123456");
        String result = jedis.rpop("queue");
        jedis.close();
        return result;
    }

    public static void main(String[] args) {
        //1.多次抢购(模拟在界面上多次点击操作)
        new Thread(){
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {//模拟页面上按钮点击
                    enqueue(String.valueOf(i));
                    try {
                        Thread.sleep(100);
                    }
                    catch (Exception e){}
                }
//                super.run();
            }
        }.start();

        //2.从队列取内容(模拟后台从队列取数据)
        new Thread(){
            @Override
            public void run() {
                for (;;) {
                    String msg = dequeue();
                    if (msg==null)continue;
                    System.out.print(msg);
                }
//                super.run();
            }
        }.start();

    }

}

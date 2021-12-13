package com.jt.demos;

import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 作业:基于redis存储商品购物车信息
 */
public class CartDemo01 {
    public static void main(String[] args) {
        //1.向购物车添加商品
        addCart(101L,201L,1);
        addCart(101L,202L,1);
        addCart(101L,203L,2);
        //2.查看购物车商品
        Map<String,String> map = listCart(101L);
        System.out.println(map);
    }

    public static Map<String, String> listCart(long userId) {

        //1.建立redis链接
        Jedis jedis = new Jedis("192.168.126.128", 6379);
//        jedis.auth("123456");
        //2.查看购物车商品
        Map<String, String> map = jedis.hgetAll("cart:" + userId);
        //3.释放redis链接
        jedis.close();
        return map;
    }

    public static void addCart(long userId, long productId, int num) {
        //1.建立redis链接
        Jedis jedis = new Jedis("192.168.126.128", 6379);
//        jedis.auth("123456");
        //2.向购物车添加商品
        //hincrBy这个函数在key不存在时会自动创建key
        jedis.hincrBy("cart:"+userId, String.valueOf(productId), num);
        //3.释放redis链接
        jedis.close();
    }
}

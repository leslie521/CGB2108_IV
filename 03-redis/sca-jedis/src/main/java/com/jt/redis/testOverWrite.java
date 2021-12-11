package com.jt.redis;

import redis.clients.jedis.Jedis;

import java.util.UUID;

public class testOverWrite {
    static String token;
    /**
     * 执行登录认证,将来这样的业务要写到认证服务器
     * @param username
     * @param password
     * @return
     */
    static String doLogin(String username,String password){
        Jedis jedis = new Jedis("192.168.126.128", 6379);
//        jedis.auth("123456");
        if (username==null || "".equals(username))
            throw new IllegalArgumentException("用户名不存在");
        if (!"jack".equals(username))
            throw new RuntimeException("用户名不正确");
        if (!"123456".equals(password))
            throw new RuntimeException("密码不正确");
        String token = UUID.randomUUID().toString();
        jedis.hset(token, "username", username);
        jedis.hset(token, "permission", "sys:resource:create");
        jedis.expire(token, 10);
        jedis.close();
        return token;
    }
    /**
     * 演示资源访问过程
     * 1)允许匿名访问(无需登录)
     * 2)登录后访问(认证通过了)
     * 3)登录后必须有权限才可以访问
     */
    static Object doResource(String token){
        if (token==null)
            throw new IllegalArgumentException("请先登录");
        Jedis jedis = new Jedis("192.168.126.128", 6379);
        String username = jedis.hget(token, "username");
        String permission = jedis.hget(token, "permission");
        jedis.close();
        if (username==null)
            throw new RuntimeException("用户名不存在");
        if (!"sys:resource:create".equals(permission))
            throw new RuntimeException("用户权限不够");

        return "your resource";
    }

    public static void main(String[] args) {
        token = doLogin("jack", "123456");
        System.out.println(token);
        Object resource = doResource(token);
        System.out.println(resource);
    }

}

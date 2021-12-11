package com.jt.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 基于某个活动的简易投票系统设计
 * 1)投票数据存储到redis (key为活动id,多个用户id的集合)
 * 2)同一个用户不能执行多次投票
 * 3)具体业务操作(投票,获取总票数,获取哪些人参与了投票)
 */
public class VoteDemo01 {
    /**
     * 获取哪些人执行了这个活动的投票
     * @param activityId
     * @return
     */
    static  Set<String> doGetMembers(String activityId){
        //1.建立连接
        Jedis jedis=new Jedis("192.168.126.128", 6379);
        //2.获取当前活动的总票数
        Set<String> smembers = jedis.smembers(activityId);
        //3.释放资源
        jedis.close();
        return smembers;
    }
    /**
     * 获取指定活动的投票总数
     * @param activityId
     * @return
     */
    static Long doCount(String activityId){
        //1.建立连接
        Jedis jedis=new Jedis("192.168.126.128", 6379);
        //2.获取当前活动的总票数
        Long count=jedis.scard(activityId);
        //3.释放资源
        jedis.close();
        return count;
    }
    /**
     * 执行投票操作
     * @param activityId
     * @param userId
     */
    static void doVote(String activityId,String userId){
        //1.建立连接
        Jedis jedis=new Jedis("192.168.126.128", 6379);
        //2.执行投票
        //2.1检查是否投过票
        Boolean flag = jedis.sismember(activityId, userId);
        //2.2执行投票或取消投票
        if(flag){
            //假如已经投过票,再投票就取消投票
            jedis.srem(activityId, userId);
        }else{
            //没有投过票则执行投票
            jedis.sadd(activityId, userId);
        }
        //3.释放资源
        jedis.close();
    }
    public static void main(String[] args) {
        String activityId="101";
        String userId1="1";
        String userId2="2";
        String userId3="3";
        //执行投票动作
        doVote(activityId, userId1);
        doVote(activityId, userId2);
        doVote(activityId, userId3);
        //获取投票的总票数
        Long aLong = doCount(activityId);
        System.out.println(aLong);
        //获取参与投票的成员
        Set<String> members= doGetMembers(activityId);
        System.out.println(members);
    }
}

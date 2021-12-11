package com.jt;

import com.jt.redis.pojo.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.Map;

@SpringBootTest
public class TestRedisTemplate {

    /**
     * RedisTemplate 是spring工程中提供的一个用于操作Redis数据库的API
     * 此对象采用了模板方法模式，对操作Redis的步骤进行了相关的封装，定义一些
     * 具体方法，后续就可以基于这些方法实现对Redis数据库的读写操作了
     */
    @Autowired
    private RedisTemplate redisTemplate;//springboot工程启动时创建

    @Test
    void testHashBlog(){
        ValueOperations vo = redisTemplate.opsForValue();
        Blog blog = new Blog();
        blog.setId(100);
        blog.setName("hello world");
        vo.set("blog", blog);
        blog =(Blog)vo.get("blog");
        System.out.println(blog);
    }

    @Test
    void testHashOper(){
        HashOperations ho = redisTemplate.opsForHash();
        ho.put("blog", "id", 100);
        ho.put("blog", "name", "tony");
//        ho.putAll();
        Object name = ho.get("blog", "name");
        System.out.println(name);
        Map blog = ho.entries("blog");
        System.out.println(blog);
    }

    /**
     * string类型数据读写操作
     */
    @Test
    void testStringOper(){
        //1.获取字符串操作对象
        //假如需要改变默认序列化方式，可以采用如下设置方式进行实现
//        redisTemplate.setKeySerializer(RedisSerializer.string());
//        redisTemplate.setValueSerializer(RedisSerializer.string());
        ValueOperations<String,String> vo = redisTemplate.opsForValue();
//        vo.set("id", 100, Duration.ofSeconds(10));
        vo.set("id", "100");
        Object id = vo.get("id");
        //直接对已存在key递增会有ERR value is not an integer or out of range
        //因为值已经采用了JDK默认的序列化方式进行了序列化存储
        //假如一定要这样写，就是可以对已存在的key进行底层，可以修改默认序列化方式
//        vo.increment("id");
        //假如希望使用RedisTemplate对象直接实现递增操作，可以用一个不存在的key实现递增
        //调用increment方法时，假如key不存在会自动创建key(会基于JDK方式序列化),但值不会JDK序列化
        Long views = vo.increment("views");
        System.out.println(id);
        System.out.println(views);
    }

    @Test
    void testListOper(){
        //向list集合放数据
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush("lstKey1", "100"); //lpush
        listOperations.leftPushAll("lstKey1", "200","300");
        listOperations.leftPush("lstKey1", "100", "105");
        listOperations.rightPush("lstKey1", "700");
        Object value= listOperations.range("lstKey1", 0, -1);
        System.out.println(value);
        //从list集合取数据
        Object v1=listOperations.leftPop("lstKey1");//lpop
        System.out.println("left.pop.0="+v1);
        value= listOperations.range("lstKey1", 0, -1);
        System.out.println(value);
    }

    @Test
    void testSetOper(){
        SetOperations setOperations=redisTemplate.opsForSet();
        setOperations.add("setKey1", "A","B","C","C");
        Object members=setOperations.members("setKey1");
        System.out.println("setKeys="+members);
        //........
    }

    @Test
    void testFlushdb(){
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                //redisConnection.flushDb();
                redisConnection.flushAll();
                return "flush ok";
            }
        });
    }

    @Test
    void testGetConnection(){
        RedisConnection connection = redisTemplate.getRequiredConnectionFactory().getConnection();
        String ping = connection.ping();
        System.out.println(ping);
    }
}

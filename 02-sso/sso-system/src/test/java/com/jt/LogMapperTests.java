package com.jt;

import com.jt.system.dao.LogMapper;
import com.jt.system.pojo.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class LogMapperTests {

    @Autowired
    private LogMapper logMapper;

    @Test
    void testInsert(){
        //构建用户行为日志对象(基于此对象存储一些用户行为日志,先用假数据)
        Log log=new Log();
        log.setUsername("cgb2108");
        log.setIp("192.168.100.200");
        log.setOperation("查询资源");
        log.setMethod("pkg.ResourceController.doSelect");
        log.setParams("");
        log.setStatus(1);
        log.setTime(100L);
        log.setCreatedTime(new Date());
        //将日志持久化到数据库
        logMapper.insert(log);
    }
}

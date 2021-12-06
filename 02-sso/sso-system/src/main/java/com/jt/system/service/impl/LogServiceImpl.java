package com.jt.system.service.impl;

import com.jt.system.dao.LogMapper;
import com.jt.system.pojo.Log;
import com.jt.system.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * @Async描述的方法底层会异步执行(不由web服务线程执行,
     * 而是交给spring自带的线程池中的线程去执行)但是@Async注解的
     * 应用有个前提,需要启动类上启动异步执行(添加@EnableAsync注解描述).
     * 优点:不会长时间阻塞web服务(例如tomcat)线程
     */
    @Async
    @Override
    public void insertLog(Log log) {
        logMapper.insert(log);
    }
}

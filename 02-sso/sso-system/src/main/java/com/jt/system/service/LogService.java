package com.jt.system.service;

import com.jt.system.pojo.Log;

/**
 * 用户行为日志业务逻辑接口定义
 */
public interface LogService {
    /**
     * 向表中记录用户行为日志
     * @param log
     */
    void insertLog(Log log);
}

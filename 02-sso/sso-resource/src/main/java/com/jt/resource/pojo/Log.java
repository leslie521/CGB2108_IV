package com.jt.resource.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 构建用户行为的日志pojo对象，基于这个对象实现用户行为日志的封装，
 * 过程如下：
 * 1)在UI端(页面上)访问我的资源
 * 2)通过AOP方式获取是谁在什么时间访问这个资源，执行的什么操作，对应的具体方法等信息
 * 3)将获取的这些信息记录到当前Log类型的对象中
 * 4)通过Feign将Log对象传递给sso-system工程
 * 5)sso-system工程会将这个对象写入到数据库
 *
 */
@Data
public class Log implements Serializable {
    private static final long serialVersionUID = -4895221416997013208L;
    private Long id;
    private String username;
    private String operation;
    private String method;
    private String params;
    private Long time;
    private String ip;
    private Date createdTime;
    private Integer status;
    private String error;
}

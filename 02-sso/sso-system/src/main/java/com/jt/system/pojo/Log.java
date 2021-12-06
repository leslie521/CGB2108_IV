package com.jt.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_logs")
public class Log implements Serializable {
    private static final long serialVersionUID = 3054471551801044482L;
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户执行这个操作时的ip地址
     */
    private String ip;
    /**
     * 这个用户表示登录用户
     */
    private String username;
    /**
     * 什么时间执行的这个操作
     */
    @TableField("createdTime")
    private Date createdTime;
    /**
     * 具体操作名
     */
    private String operation;
    /**
     * 具体操作对应的方法(这里要求写类全名+方法名)
     */
    private String method;
    /**
     * 执行方法时，传递的实际参数
     */
    private String params;
    /**
     * 执行这个操作是成功了还是失败了
     */
    private Integer status;
    /**
     * 当状态是失败状态时，我们要记录失败的原因
     */
    private String error;
    /**
     * 耗时(执行这个操作消耗的时间)
     */
    private Long time;
}

package com.jt.system.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 基于此对象封装登录用户信息(例如从数据库查询到的用户信息)
 */
@Data
//@TableName("tb_users")
public class User implements Serializable {
    private static final long serialVersionUID = 4831304712151465443L;
    private Long id;
    private String username;
    private String password;
    private String status;
}

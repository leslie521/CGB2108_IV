package com.jt.redis.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("tb_menus")
public class Menu implements Serializable {
    private static final long serialVersionUID = 6576926898823144301L;
    @TableId(type = IdType.AUTO)
    private Long Id;
    private String name;
    private String permission;

    @Override
    public String toString() {
        return "Menu{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}

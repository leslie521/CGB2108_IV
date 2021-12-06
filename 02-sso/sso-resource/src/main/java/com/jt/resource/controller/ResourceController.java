package com.jt.resource.controller;

import com.jt.resource.annotation.RequiredLog;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    /**
     * 查询资源
     * @PreAuthorize描述方法时表示此方法的访问需要授权
     * @RequiredLog注解描述方法时，表示要获取用户行为日志，此时这个方法
     * 就是一个日志切入点方法(这个方法是不是切入点方法，谁说了算？)
     */
    @RequiredLog("查询我的资源") //我们自己定义的注解
    @PreAuthorize("hasAuthority('sys:res:list')") //spring security官方定义
    @GetMapping
    public String doSelect(){
        return "Select Resource ok";
    }
    /**
     * 创建资源
     * @return
     */
    @RequiredLog("创建我的资源")
    @PreAuthorize("hasAuthority('sys:res:create')")
    @PostMapping
    public String doCreate(){
        return "Create Resource OK";
    }
    /**
     * 修改资源
     * @return
     */
    @PreAuthorize("hasAuthority('sys:res:update')")
    @PutMapping
    public String doUpdate(){
        return "Update Resource OK";
    }
    /**
     * 删除资源
     * @return
     */
    @DeleteMapping
    public String doDelete(){
        return "Delete resource ok";
    }
}

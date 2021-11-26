package com.jt.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**定义Controller对象(这个对象在spring mvc中给他的定义是handler),
 * 基于此对象处理客户端的请求*/
@RestController
public class ProviderController {

    //@Value默认读取项目配置文件中配置的内容
    //8080为没有读到server.port的值时,给定的默认值
    @Value("${server.port:8080}")
    private String server;

    //http://localhost:8081/provider/echo/tedu
    @GetMapping("/provider/echo/{msg}")
    public String doRestEcho1(@PathVariable String msg) throws InterruptedException {
        //模拟耗时操作
//        Thread.sleep(5000);
        return server + " say:hello " + msg;
    }
}

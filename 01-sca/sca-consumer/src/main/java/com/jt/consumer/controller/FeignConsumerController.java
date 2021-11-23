package com.jt.consumer.controller;

import com.jt.consumer.service.RemoteProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class FeignConsumerController {

    @Autowired
    private RemoteProviderService remoteProviderService;

    /**基于feign方式的服务调用*/
    @RequestMapping("/echo/{msg}")
    public String doFeignEcho(@PathVariable("msg") String msg){
        //基于feign方式进行远端服务调用(前提是服务必须存在)
        return remoteProviderService.echoMessage(msg);
    }
}

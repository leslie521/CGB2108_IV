package com.jt.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RefreshScope
@RestController
public class ProviderCacheController {

    @Value("${userLocalCache:false}")
    private boolean userLocalCache;

    @GetMapping("/provider/cache")
    public String userLocalCache(){
        return "userLocalCache.state: " + userLocalCache;
    }

    //CopyOnWriteArrayList
    //homework
    @RequestMapping("/provider/cache02")
    public List<String> doUseLocalCache02(){
        //假设这些数据来自数据库
        List<String> cateList= Arrays.asList("电器","服装","医疗健康");
        return cateList;
    }
}

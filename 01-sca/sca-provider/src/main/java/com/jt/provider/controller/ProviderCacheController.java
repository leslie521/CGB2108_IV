package com.jt.provider.controller;

import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RefreshScope
@RestController
public class ProviderCacheController {

    @Value("${userLocalCache:false}")
    private boolean userLocalCache;//缓存开关

    @GetMapping("/provider/cache")
    public String userLocalCache(){
        return "userLocalCache.state: " + userLocalCache;
    }

    //CopyOnWriteArrayList
    //构建一个线程安全的List对象，基于此对象存储从数据库获取的一些数据
    private List<String> cache = new CopyOnWriteArrayList<>();

    //homework
    @RequestMapping("/provider/cache02")
    public List<String> doUseLocalCache02(){
        if (!userLocalCache){
            System.out.println("Get Data From Databases");
            return Arrays.asList("电器","服装","医疗健康");
        }

        if (cache.isEmpty()){
            synchronized (cache) {
                if (cache.isEmpty()) {
                    //假设这些数据来自数据库
                    System.out.println("从数据库取数据");
                    List<String> cateList = Arrays.asList("电器", "服装", "医疗健康");
                    cache.addAll(cateList);
                }
            }
        }
        return cache;
    }
}

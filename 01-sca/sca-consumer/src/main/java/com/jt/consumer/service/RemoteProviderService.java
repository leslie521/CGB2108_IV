package com.jt.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 这里的@FeignClient注解用于描述Feign远程服务调用接口，假如在项目配置类
 * 或启动类上添加了@EnableFeignClients注解，系统会在启动时，扫描@FeignClient
 * 注解描述的接口，并为接口创建实现类对象(这个实现类我们称之为代理对象)，此对象
 * 内部会封装对远程服务调用的过程。
 * @FeignClient 注解中name属性的值有两个层面的含义：
 * 1)RemoteProviderService接口类型对象交给spring管理时，这个对象(bean)的名字
 * 2)远程调用的服务名
 * 假如我们不希望@FeignClient注解描述的接口对应的Bean名字不是name属性的值，我们
 * 可以配置contextId，将此属性的值作为bean对象名字
 */
@FeignClient(name = "sca-provider",contextId = "remoteProviderService",
        fallbackFactory = ProviderFallbackFactory.class)
public interface RemoteProviderService {//声明式接口(注意只做声明，声明要调用的远程服务以及对应的资源)

    @GetMapping("/provider/echo/{msg}")//前提是远端需要有这个服务
    public String echoMessage(@PathVariable("msg") String msg);
}

package com.jt.consumer.service;

import com.jt.service.factory.ProviderFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//sca-provider为nacos中的服务名,其中@FeignClient描述的接口底层会为其创建实现类。
@FeignClient(name = "sca-provider",contextId = "remoteProviderService",
        fallbackFactory = ProviderFallbackFactory.class)
public interface RemoteProviderService {

    @GetMapping("/provider/echo/{string}")//前提是远端需要有这个服务
    public String echoMessage(@PathVariable("string") String string);
}

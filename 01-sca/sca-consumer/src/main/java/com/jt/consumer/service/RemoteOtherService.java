package com.jt.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//sca-consumer
//1)RemoteProviderService
//2)RemoteOtherService
@FeignClient(name = "sca-provider",contextId = "remoteOtherService")
public interface RemoteOtherService {

    @GetMapping("/dosomething")
    public String doSomething();
}

package com.jt.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "sca-provider")
public interface RemoteOtherService {

    @GetMapping("/dosomething")
    public String doSomething();
}

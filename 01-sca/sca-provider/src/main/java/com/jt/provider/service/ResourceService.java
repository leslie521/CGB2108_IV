package com.jt.provider.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;


@Service
public class ResourceService {

    @SentinelResource(value = "doGetResource",
                      blockHandlerClass = ResourceBlockHandle.class,
                      blockHandler = "handleBlock")
    public String doGetResource(){
        return "My resource";
    }
}

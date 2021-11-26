package com.jt.provider.controller;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.jt.provider.service.ResourceBlockHandle;
import com.jt.provider.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
public class ProviderSentinelController {

    @GetMapping("/sentinel01")
    public String doSentinel01(){
        return "sentinel 01 test ...";
    }

    @GetMapping("/sentinel02")
    public String doSentinel02(){
        return "sentinel 02 test ...";
    }

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/sentinel03")
    public String doSentinel03(){
        String message = resourceService.doGetResource();
        return message;
    }
}

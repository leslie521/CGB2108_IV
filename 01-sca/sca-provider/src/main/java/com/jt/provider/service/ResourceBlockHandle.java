package com.jt.provider.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResourceBlockHandle {

    public static String handleBlock(BlockException blockException){
//        exception.printStackTrace();
        log.error("block exception {}","被限流了");
        return "访问太频繁，稍等片刻再访问！！！";
    }
}

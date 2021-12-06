package com.jt.resource.service;

import com.jt.resource.pojo.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "sso-system",contextId = "remoteLogService")
public interface RemoteLogService {
    @PostMapping("/log")
    public void insertLog(@RequestBody Log log);
}

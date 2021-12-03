package com.jt.auth.service;

import com.jt.auth.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 基于这个接口进行sso-system服务的调用
 */
@FeignClient(value = "sso-system",contextId = "remoteUserService")
public interface RemoteUserService {

    @GetMapping("/user/login/{username}")
    User selectUserByUsername(@PathVariable("username") String username);

    @GetMapping("/user/permission/{userId}")
    List<String> selectUserPermissions(@PathVariable("userId") Long userId);
}

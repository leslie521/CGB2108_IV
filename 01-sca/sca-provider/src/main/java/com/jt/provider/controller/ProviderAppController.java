package com.jt.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ProviderAppController {

    @Value("${app.secret:SSS}")
    private String appSecret;

    @GetMapping("/provider/secret")
    public String appSecret(){
        return "the app-secret is: " + appSecret;
    }
}

package com.jt.provider.controller;

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
}

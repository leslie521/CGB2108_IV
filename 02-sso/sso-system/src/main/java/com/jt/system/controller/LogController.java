package com.jt.system.controller;

import com.jt.system.pojo.Log;
import com.jt.system.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping
    public void doInsertLog(@RequestBody Log log){
        logService.insertLog(log);
    }
}

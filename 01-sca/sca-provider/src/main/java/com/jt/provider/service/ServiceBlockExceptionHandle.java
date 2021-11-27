package com.jt.provider.service;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceBlockExceptionHandle implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       BlockException e) throws Exception {
        response.setCharacterEncoding("utf-8");

        response.setContentType("application/json;charser=utf-8");

        Map<String,Object> map = new HashMap<>();
        map.put("status", 429);
        map.put("message", "访问太频繁了！！！");

        String jsonStr = new ObjectMapper().writeValueAsString(map);
        PrintWriter writer = response.getWriter();
        writer.println(jsonStr);
        writer.flush();
    }
}

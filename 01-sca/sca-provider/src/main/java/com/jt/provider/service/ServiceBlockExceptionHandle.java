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

/**
 * 限流，降级异常处理对象
 */
@Component
public class ServiceBlockExceptionHandle implements BlockExceptionHandler {

    /**
     * 出现限流或降级时，会自动执行handle方法
     * @param request
     * @param response
     * @param e
     * @throws Exception
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       BlockException e) throws Exception {
        //1.设置响应数据的编码(中文可能会出现乱码)
        response.setCharacterEncoding("utf-8");
        //2.告诉浏览器你要响应的内容类型以及编码
        response.setContentType("application/json;charser=utf-8");
        //3.构建响应数据
        Map<String,Object> map = new HashMap<>();
        map.put("status", 429);
        map.put("message", "访问太频繁了！！！");
        //将map转换为json格式字符串
        String jsonStr = new ObjectMapper().writeValueAsString(map);
        //4.将响应数据写到客户端
        PrintWriter writer = response.getWriter();
        writer.println(jsonStr);
        writer.flush();
    }
}

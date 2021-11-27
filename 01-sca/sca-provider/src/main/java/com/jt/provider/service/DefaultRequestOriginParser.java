package com.jt.provider.service;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class DefaultRequestOriginParser implements RequestOriginParser {
//    @Override
//    public String parseOrigin(HttpServletRequest request) {
//        String origin = request.getParameter("origin");//这里的参数名会与请求中的参数名一致
//        return origin;
//    }

//解析请求源数据
    @Override
    public String parseOrigin(HttpServletRequest request) {
        //获取访问请求中的ip地址,基于ip地址进行黑白名单设计（例如在流控应用栏写ip地址）
        String ip = request.getRemoteAddr();
        System.out.println("ip="+ip);
        return ip;
    }//授权规则中的黑白名单的值,来自此方法的返回值
}

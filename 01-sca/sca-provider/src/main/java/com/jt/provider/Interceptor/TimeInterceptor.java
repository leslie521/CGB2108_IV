package com.jt.provider.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

/**
 * 定义一个基于时间进行访问控制的拦截器，规范是spring mvc中的HandlerInterceptor，
 * 写好拦截器后，这个拦截器要对哪些请求进行拦截还要进行配置
 */
//@Component
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        System.out.println("==preHandle()==");
        //1.获取当前时间
        LocalTime now = LocalTime.now();//LocalTime为jdk8中的一个获取当前时间的api
        //2.获取当前的小时并进行逻辑判断
        int hour = now.getHour();
        System.out.println("hour="+hour);
        if (hour<8 || hour>22){
            throw new RuntimeException("请在8点-22点之间进行访问！！");//return false
        }

        return true;//false请求到此结束，true表示放行，会去执行后续的拦截器或controller对象
    }
}

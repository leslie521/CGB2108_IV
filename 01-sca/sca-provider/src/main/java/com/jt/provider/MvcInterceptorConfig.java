package com.jt.provider;

import com.jt.provider.Interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 定义Spring Web MVC 配置，需要实现 spring中的WebMvcConfigurer接口
 */
@Configuration
public class MvcInterceptorConfig implements WebMvcConfigurer {

    /*方法1*/
//    @Autowired
//    private TimeInterceptor timeInterceptor;

    /**
     * 注册拦截器(添加到spring容器)，并指定拦截规则
     * @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(timeInterceptor)
//                .addPathPatterns("/provider/sentinel01");
//    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*方法2*/
        registry.addInterceptor(new TimeInterceptor())
                .addPathPatterns("/provider/sentinel01");
    }
}

package com.jt.auth.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 当我们在执行登录操作时,底层逻辑(了解):
 * 1)Filter(过滤器)
 * 2)AuthenticationManager (认证管理器)
 * 3)AuthenticationProvider(认证服务处理器)
 * 4)UserDetailsService(负责用户信息的获取及封装)
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 初始化加密对象
     * 此对象提供了一种不可逆的加密方式,相对于md5方式会更加安全
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 定义认证管理器对象，这个对象负责完成用户信息的认证，
     * 即判定用户身份信息的合法性，在基于oauth2协议完成认
     * 证时，需要此对象，所以这里讲此对象拿出来交给spring管理
     * @return
     * @throws Exception
     */
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    /**配置认证规则*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);//默认所有请求都要认证
        //1.禁用跨域攻击(先这么写，不写会报403异常)
        http.csrf().disable();
        //2.放行所有资源的访问(后续可以基于选择对资源进行认证和放行)
        http.authorizeRequests().anyRequest().permitAll();
        //3.自定义定义登录成功和失败以后的处理逻辑(可选)
        //假如没有如下设置登录成功会显示404
        http.formLogin()//这句话会对外暴露一个登录路径/login
                .successHandler(successHandler())
                .failureHandler(failureHandler());

    }

    //定义认证成功处理器
    //登录成功以后返回json数据
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        //lambda
        return (request,response,authentication)->{
            //构建map对象封装到要响应到客户端的数据
            Map<String,Object> map = new HashMap<>();
            map.put("status",200);
            map.put("message", "login ok");
            //将map对象转换为json格式字符串并写到客户端
            writeJsonToClient(response,map);
        };
    }

    //定义登录失败处理器
    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return (request,response,exception)->{
            //构建map对象封装到要响应到客户端的数据
            Map<String,Object> map = new HashMap<>();
            map.put("status",500);
            map.put("message", "login error");
            //将map对象转换为json格式字符串并写到客户端
            writeJsonToClient(response,map);
        };
    }

    private void writeJsonToClient(HttpServletResponse response,
                                   Map<String, Object> map) throws IOException {
        //将map对象,转换为json
        String json = new ObjectMapper().writeValueAsString(map);
        //设置响应数据的编码方式
        response.setCharacterEncoding("utf-8");
        //设置响应数据的类型
        response.setContentType("application/json;charset=utf-8");
        //将数据响应到客户端
        PrintWriter writer = response.getWriter();
        writer.println(json);
        writer.flush();
    }

}

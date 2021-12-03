package com.jt.auth.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
     * 构建加密算法对象，基于此算法对用户端输入的密码进行加密
     * Client-->(username,password)
     * Server-->(对未加密的密码进行加密然后与数据库已加密的密码进行比对)
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 定义认证管理器对象，这个对象负责完成用户信息的认证，
     * 即判定用户身份信息的合法性，在基于oauth2协议完成认
     * 证时，需要此对象，所以这里讲此对象拿出来交给spring管理
     * 目的是与后续oauth2协议进行整合。
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 配置认证规则(系统默认规则是所有资源必须先认证才能访问)
     * 1)哪些请求必须认证，哪些请求无需认证
     * 2)配置认证成功和失败处理
     */
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
        // http.authorizeRequests()
        //        .mvcMatchers("/default.html").authenticated()//认证
        //        .anyRequest().permitAll();//其它都放行
        //3.设置认证结果处理器(默认认证成功以后会跳转到一个index.html页面)
        //formLogin方法执行后会创建一个/login路径

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

    /*认证成功管理器返回的json串第二种方式，认证失败时同理*/
//    private AuthenticationSuccessHandler successHandler(){
//        return new AuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(
//                    HttpServletRequest request,
//                    HttpServletResponse response,
//                    Authentication authentication) throws IOException, ServletException {
//                Map<String,Object> map = new HashMap<>();
//                map.put("status",200);
//                map.put("message", "login ok");
//                //将map对象转换为json格式字符串并写到客户端
//                writeJsonToClient(response,map);
//            }
//        };
//    }

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

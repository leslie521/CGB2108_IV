package com.jt.auth.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 在这个对象中负责将所有的认证和授权相关配置进行整合，例如
 * 业务方面：
 * 1)如何认证(认证逻辑的设计)
 * 2)认证通过以后如何颁发令牌(令牌的规范)
 * 3)为谁颁发令牌(客户端标识，client_id,...)
 * 技术方面：
 * 1)SpringSecurity (提供认证和授权的实现)
 * 2)TokenConfig(提供了令牌的生成，存储，校验)
 * 3)Oauth2(定义了一套认证规范，例如为谁发令牌，都发什么，...)
 */
@AllArgsConstructor //生成一个全参构造函数
@Configuration
@EnableAuthorizationServer //启动认证和授权
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private TokenStore tokenStore;
    private PasswordEncoder passwordEncoder;
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * oauth2中的认证细节配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        //super.configure(endpoints);
        endpoints
        //由谁完成认证？(认证管理器)
        .authenticationManager(authenticationManager)
        //谁负责访问数据库？(认证时需要两部分信息：一部分来自客户端，一部分来自数据库)
        .userDetailsService(userDetailsService)
        //支持对什么请求进行认证（默认支持post方式）
        .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST)
        //认证成功以后令牌如何生成和存储？(默认令牌生成UUID.randomUUID(),存储方式为内存)
        .tokenServices(tokenService());

    }

    //系统底层在完成认证以后会调用TokenService对象的相关方法
    //获取TokenStore，基于tokenStore获取token对象
    private AuthorizationServerTokenServices tokenService() {
        return null;
    }




}

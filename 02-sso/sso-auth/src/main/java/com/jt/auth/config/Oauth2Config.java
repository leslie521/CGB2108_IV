package com.jt.auth.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

/**
 * Oauth2 是一种认证授权规范，它基于认证和授权定义了一套规则，在这套规则中规定了
 * 实现一套认证授权系统需要哪些对象：
 * 1)系统资源(数据)
 * 2)资源拥有者(用户)
 * 3)管理资源的服务器
 * 4)对用户进行认证和授权的服务器
 * 5)客户端系统(负责提交用户身份信息的系统)
 *
 * 思考：对于一个认证授权系统来讲，是否：
 * 1)提供一个认证的入口？(客户端去哪里认证)
 * 2)客户端应该携带什么信息去认证？(username,password,....)
 * 3)服务端通过谁去对客户端进行认证(一个负责认证的对象)？
 *
 */
@AllArgsConstructor //生成一个全参构造函数
@Configuration
@EnableAuthorizationServer //在oauth2规范中启动认证和授权
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private TokenStore tokenStore;
    private PasswordEncoder passwordEncoder;
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 提供一个负责认证授权的对象？(完成客户端认证后会颁发令牌，默认令牌格式是uuid方式的)
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        //super.configure(endpoints);
        endpoints
        //由谁完成认证？(认证管理器)设置认证授权对象
        .authenticationManager(authenticationManager)
        //谁负责访问数据库？(认证时需要两部分信息：一部分来自客户端，一部分来自数据库)
        .userDetailsService(userDetailsService)
        //支持对什么请求进行认证（默认支持post方式）
        .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST)
        //认证成功以后令牌如何生成和存储？(默认令牌生成UUID.randomUUID(),存储方式为内存)
        //设置令牌业务对象(此对象提供令牌创建及有效机制设置)
        .tokenServices(tokenService());

    }

    //系统底层在完成认证以后会调用TokenService对象的相关方法
    //获取TokenStore，基于tokenStore获取token对象
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        //1.构建TokenService对象(此对象提供了创建，获取，刷新token的方法)
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        //2.设置令牌生成和存储策略
        tokenServices.setTokenStore(tokenStore);
        //3.设置是否支持令牌刷新(访问令牌过期了，是否支持通过令牌刷新机制，延长令牌有效期)
        tokenServices.setSupportRefreshToken(true);
        //4.设置令牌增强(默认令牌会比较简单，没有业务数据，
        //就是简单随机字符串，但现在希望使用jwt方式)
        TokenEnhancerChain tokenEnhancer = new TokenEnhancerChain();
        tokenEnhancer.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
        tokenServices.setTokenEnhancer(tokenEnhancer);
        //5.设置访问令牌有效期
        tokenServices.setAccessTokenValiditySeconds(3600);//1小时
        //6.设置刷新令牌有效期
        tokenServices.setRefreshTokenValiditySeconds(3600*72);//3天
        return tokenServices;
    }

    /**
     * 提供一个认证的入口(客户端去哪里认证)？(http://ip:port/.....)
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security)
            throws Exception {
//        super.configure(security); //默认
        security
        //对外发布认证入口(/oauth/token),认证通过服务端会生成一个令牌
         //公开oauth/token_key端点
        .tokenKeyAccess("permitAll()") //return this
        //对外发布检查令牌的入口(/oauth/check_token)
        .checkTokenAccess("permitAll()")
        //3.允许客户端直接通过表单方式提交认证
        .allowFormAuthenticationForClients();
    }

    /**
     * 认证中心是否要给所有的客户端发令牌呢？假如不是，那要给哪些客户端
     * 发令牌，是否在服务端有一些规则的定义呢？
     * 例如：老赖不能做飞机，不能做高铁
     * //定义客户端应该携带什么信息去认证？
     * //指明哪些对象可以到这里进行认证(哪个客户端对象需要什么特点)。
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
//        super.configure(clients); //默认
        clients.inMemory()
                //客户端标识
                .withClient("gateway-client")
                //客户端密钥(随意)
                .secret(passwordEncoder.encode("123456"))
                //作用域(在这里可以理解为只要包含我们规定信息的客户端都可以进行认证)
                .scopes("all")
                //指定认证类型(码密,刷新令牌，三方令牌，...)
                .authorizedGrantTypes("password","refresh_token");
    }
}

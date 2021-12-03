package com.jt.auth.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 使用自定义用户名和密码执行登录操作的逻辑实现(系统默认用户为user，密码是控制台密码~随机字符串)
 * 简易认证流程(Spring Security)：这个执行链底层框架已经设计好，我们只需要执行。
 * 1)客户端提交用户名和密码给服务端
 * 2)服务端调用Spring Security框架中的过滤器(Filters)对用户名和密码进行预处理
 * 3)过滤器(Filters)将用户名和密码传递给认证管理器(AuthenticationManager)完成用户身份认证
 * 4)认证管理器会调用UserDetailsService对象获取远端服务或数据库中的用户信息，然后
 * 与客户端提交的用户信息进行比对(这个比对过程就是认证)。
 * 5)认证通过则基于用户权限对用户进行资源访问授权。
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 基于用户名获取数据库中的用户信息
     * @param username 这个username来自客户端
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        //基于feign方式获取远程数据并封装
        //1.基于用户名获取用户信息
        com.jt.auth.pojo.User user = remoteUserService.selectUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        //2.基于用于id查询用户权限
        List<String> permissions = remoteUserService.selectUserPermissions(user.getId());
        log.info("permissions {}",permissions);
        //3.对查询结果进行封装并返回(交给认证管理器去完成认证操作)
//        List<GrantedAuthority> authorities =
//                AuthorityUtils.createAuthorityList(permissions.toArray(new String[]{}));
        User userInfo = new User(username,user.getPassword(),
                AuthorityUtils.createAuthorityList(permissions.toArray(new String[]{})));
        //......
        return userInfo;
        //返回给认证中心,认证中心会基于用户输入的密码以及数据库的密码做一个比对
    }
}

package com.jt.system.service.Impl;

import com.jt.system.dao.UserMapper;
import com.jt.system.pojo.User;
import com.jt.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByName(String username) {
        return userMapper.selectUserByName(username);
    }

    @Override
    public List<String> selectUserPermission(Long userId) {
        return userMapper.selectUserPermission(userId);
    }
}

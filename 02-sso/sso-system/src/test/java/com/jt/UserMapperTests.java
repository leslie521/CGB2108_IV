package com.jt;

import com.jt.system.dao.UserMapper;
import com.jt.system.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelectUserByName(){
        User user = userMapper.selectUserByName("admin");
        System.out.println(user);
    }

    @Test
    void testSelectUserPermission(){
        List<String> Permissions = userMapper.selectUserPermissions(1L);
        System.out.println(Permissions);
    }

}

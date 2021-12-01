package com.jt.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.system.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 定义用户数据访问层接口，基于此接口定义数据访问逻辑
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 基于用户名从数据库查询用户信息
     * @param username
     * @return
     */
    @Select("select * from tb_users where username = #{username}")
    User selectUserByName(String username);//JDK8以上可以省略@Param("username")

    /**
     * 基于用户id查询用户权限
     * @param userId
     * @return
     * 涉及到的表:tb_user_roles,tb_role_menus,tb_menus
     */
    @Select("select distinct m.permission from tb_user_roles ur " +
            "        join tb_role_menus rm " +
            "            on ur.role_id = rm.role_id " +
            "        join tb_menus m " +
            "            on rm.menu_id = m.id " +
            "where ur.user_id = #{userId}")
    List<String> selectUserPermissions(Long userId);
}

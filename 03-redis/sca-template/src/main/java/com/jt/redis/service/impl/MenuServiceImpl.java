package com.jt.redis.service.impl;

import com.jt.redis.dao.MenuMapper;
import com.jt.redis.pojo.Menu;
import com.jt.redis.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;

/**操作菜单的业务对象*/
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations vo;

    /**
     * 基于id查询菜单信息
     * @param id
     * @return
     */
    @Override
    public Menu selectById(Long id) {
        //1。基于id查询Redis,假如有对应的记录则直接返回
//        ValueOperations vo = redisTemplate.opsForValue();
        Object obj = vo.get(String.valueOf(id));
        if (obj!=null)
            return (Menu) obj;
        //2.假如redis中没有我们需要的数据，则查询数据库，然后将查询结果再存储到redis并返回
        Menu menu = menuMapper.selectById(id);
        vo.set(String.valueOf(id), menu, Duration.ofSeconds(60));
        return menu;
    }

    /**
     * 新增menu信息
     * @param menu
     * @return
     */
    @Override
    public Menu insertMenu(Menu menu) {
        //1.写mysql
        menuMapper.insert(menu);
        //2.写Redis
//        ValueOperations vo = redisTemplate.opsForValue();
        vo.set(String.valueOf(menu.getId()), menu);
        return menu;
    }

    /**
     * 更新menu信息
     * @param menu
     * @return
     */
    @Override
    public Menu updateMenu(Menu menu) {
        //更新mysql
        menuMapper.updateById(menu);
        //更新redis (保证redis与mysql中数据的一致性)
        //ValueOperations valueOperations = redisTemplate.opsForValue();
        vo.set(String.valueOf(menu.getId()), menu);
        return menu;
    }
}

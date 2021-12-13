package com.jt.redis.service.impl;

import com.jt.redis.dao.MenuMapper;
import com.jt.redis.pojo.Menu;
import com.jt.redis.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class DefaultMenuService implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * @Cacheable 注解描述的方法为一个缓存切入点方法，
     * 这里表示将查询到的结果写缓存中(例如redis)
     * @param id
     * @return
     */
    @Cacheable(value = "menuCache",key ="#id")
    @Override
    public Menu selectById(Long id) {
        return menuMapper.selectById(id);
    }
    /**
     * @CachePut 注解描述的方法为一个切入点方法，此方法执行后，
     * 会将方法的返回值存储到缓存中。
     * @param menu
     * @return
     */
    @CachePut(value = "menuCache",key ="#menu.id")
    @Override
    public Menu insertMenu(Menu menu) {
        menuMapper.insert(menu);
        return menu;//这里返回的menu已经有id了
    }
    /**
     * @CachePut 注解描述的方法为一个切入点方法，此方法执行后，
     * 会将方法的返回值更新到缓存中。
     * @param menu
     * @return
     */
    @CachePut(value = "menuCache",key ="#menu.id")
    @Override
    public Menu updateMenu(Menu menu) {
        menuMapper.updateById(menu);
        return menu;
    }
}

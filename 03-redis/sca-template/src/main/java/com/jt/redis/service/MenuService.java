package com.jt.redis.service;

import com.jt.redis.pojo.Menu;

public interface MenuService {
    /**
     * 基于id查找菜单对象,先查redis,redis没有再查数据库
     * @param id
     * @return
     */
    Menu selectById(Long id);
    /**
     * 向表中写入一条菜单信息,与此同时也要向redis写入一样的数据
     * @param menu
     * @return
     */
    Menu insertMenu(Menu menu);
    /**
     * 更新表中数据,与此同时也要更新redis中的数据
     * @param menu
     * @return
     */
    Menu updateMenu(Menu menu);
    //.....
}

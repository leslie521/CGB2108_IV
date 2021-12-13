package com.jt;

import com.jt.redis.pojo.Menu;
import com.jt.redis.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MenuServiceTests {

    @Autowired
    private MenuService menuService;

    @Test
    void testUpdateMenu(){
        Menu menu = menuService.selectById(4L);
        menu.setPermission("sys:resource:add");
        menuService.updateMenu(menu);
    }

    @Test
    void testInsertMenu(){
        Menu menu = new Menu();
        menu.setName("insert something");
        menu.setPermission("sys:insert");
        menuService.insertMenu(menu);
        System.out.println(menu);
    }

    @Test
    void testSelectById(){
        Menu menu = menuService.selectById(4l);
        System.out.println(menu);
    }
}

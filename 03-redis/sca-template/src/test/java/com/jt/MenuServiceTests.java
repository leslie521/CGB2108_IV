package com.jt;

import com.jt.redis.pojo.Menu;
import com.jt.redis.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MenuServiceTests {

    @Autowired
    @Qualifier(value = "defaultMenuService")
    private MenuService menuService;

    @Test
    void testUpdateMenu(){
        Menu menu = menuService.selectById(6L);
        menu.setPermission("sys:resource:world");
        menuService.updateMenu(menu);
    }

    @Test
    void testInsertMenu(){
        Menu menu = new Menu();
        menu.setName("insert something");
        menu.setPermission("sys:resources:hello");
        menuService.insertMenu(menu);
        System.out.println(menu);
    }

    @Test
    void testSelectById(){
        Menu menu = menuService.selectById(6l);
        System.out.println(menu);
    }
}

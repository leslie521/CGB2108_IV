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
        menu.setPermission("sys:resource:import");
        menuService.updateMenu(menu);
    }

    @Test
    void testInsertMenu(){
        Menu menu = new Menu();
        menu.setName("insert eight");
        menu.setPermission("sys:resources:welcome");
        menuService.insertMenu(menu);
        System.out.println(menu);
    }

    @Test
    void testSelectById(){
        Menu menu = menuService.selectById(1l);
        System.out.println(menu);
    }
}

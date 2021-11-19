package com.jt.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

@SpringBootTest
public class StringTests {

    @Test
    public void methodTest(){
        boolean str = StringUtils.isEmpty("anita");
        System.out.println(str);
    }

}

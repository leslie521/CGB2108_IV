package com.jt.util;

import com.jt.common.util.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class StringTests {

    @Test
    public void methodTest(){
        boolean empty = StringUtils.isEmpty("anita");
        System.out.println(empty);
    }

}

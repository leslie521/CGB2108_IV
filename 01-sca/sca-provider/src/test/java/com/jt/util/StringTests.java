package com.jt.util;

import com.jt.common.util.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class StringTests {

    @Test
    public void methodTest(){
        boolean empty = StringUtils.isEmpty("anita");
        String str1 = "";
        String str2 = "string";
        System.out.println(str1.equals(str2));
        System.out.println(empty);
    }

}

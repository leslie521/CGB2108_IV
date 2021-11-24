package com.jt.util;

import org.junit.jupiter.api.Test;

public class TestArgs {
    @Test
    public void test(){
        int num = 10;
        System.out.println("-----------二进制数----------");
        printInfo(num);
        System.out.println("-----------十进制数----------");
        System.out.println(num);

        /*左移一位*/
        num = num << 1;
        System.out.println("-----------二进制数----------");
        printInfo(num);
        System.out.println("-----------十进制数----------");
        System.out.println(num);

        /*右移一位*/
        num = num >> 1;
        System.out.println("-----------二进制数----------");
        printInfo(num);
        System.out.println("-----------十进制数----------");
        System.out.println(num);

    }

    private void printInfo(int num) {
        System.out.println(Integer.toBinaryString(num));
    }
}

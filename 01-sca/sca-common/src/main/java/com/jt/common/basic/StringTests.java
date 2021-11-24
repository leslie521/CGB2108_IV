package com.jt.common.basic;

public class StringTests {
    public static void main(String[] args) {
        String url=String.format("http://%s/provider/echo/{msg}","sca-provider");
        System.out.println(url);
        //System.out.println("a+b="+100);
        System.out.printf("%s a+b=%d","经过计算",100);
    }
}

package com.jt.redis;

public class test {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "he"+"llo";
        String s3 = new String("hello");
        System.err.println(s1 == s2);
        System.err.println(s1 == s3);
    }
}

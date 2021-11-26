package com.jt.common.cache;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTests {
    private static List<String> cache=new CopyOnWriteArrayList<>();
    public static List<String> selectAll(){
        if(cache.isEmpty()) {
            synchronized (cache) {
                if (cache.isEmpty()) {
                    //假设这些数据来自数据库
                    List<String> cateList =
                            Arrays.asList("A", "B", "C");
                    cache.addAll(cateList);
                }
            }
        }
        return cache;
    }
    public static void main(String[] args) {
        //System.out.println(selectAll());
        Thread t1=new Thread(){
            @Override
            public void run() {
                System.out.println(selectAll());
            }
        };
        Thread t2=new Thread(){
            @Override
            public void run() {
                System.out.println(selectAll());
            }
        };
        Thread t3=new Thread(){
            @Override
            public void run() {
                System.out.println(selectAll());
            }
        };
        t1.start();
        t2.start();
        t3.start();
    }
}

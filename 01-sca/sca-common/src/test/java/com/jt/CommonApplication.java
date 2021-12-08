package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class CommonApplication {

    @Test
    void testThread(){
        Inter inter1 = new InterImpl();
        inter1.eat();
        inter1.play();
        Runnable target = new MyRunnable();
        Thread t = new Thread(target);
//        t.run();
        t.start();
//        InterImpl inter = new InterImpl();
    }
}

interface Inter{
    public void eat();
    public void play();
}

class InterImpl implements Inter{

    @Override
    public void eat() {
        System.out.println("I am eat()");
    }

    @Override
    public void play() {
        System.out.println("I am play()");
    }
}

class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("I am the Runnable实现类的重写 run方法");
    }
}

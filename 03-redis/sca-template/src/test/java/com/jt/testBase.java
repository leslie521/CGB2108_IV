package com.jt;

public class testBase {
    public static void main(String[] args) {
        Two two = new Two("ady");
//        two.user();
    }
}
class Over {
    void user(){
        System.out.println("user()");
    }
    Over(){
        System.out.println("Over() 无参构造");
    }
    Over(int age){
        System.out.println("Over\t" + age);
    }
}
class Two extends Over{

    Two(){
//        super();
        System.out.println("testTwo() 构造");
    }
    Two(String name){
//        super(18);
        System.out.println("two\t" + name);
    }
}
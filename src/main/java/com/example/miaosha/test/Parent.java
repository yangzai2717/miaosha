package com.example.miaosha.test;

public class Parent {

    private void testParent(){
        System.out.println("这是父类方法");
    }

    static {

        System.out.println("这是父类静态");
    }

    public Parent() {
        testParent();
        System.out.println("这是父类构造");
    }
}

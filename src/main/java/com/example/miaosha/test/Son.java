package com.example.miaosha.test;

public class Son extends Parent{

    private void testSon(){
        System.out.println("这是子类方法");
    }

    static {
        System.out.println("这是子类静态");
    }

    public Son() {
        testSon();
        System.out.println("这是子类构造");
    }

    public static void main(String[] args) {
        new Son();
    }
}

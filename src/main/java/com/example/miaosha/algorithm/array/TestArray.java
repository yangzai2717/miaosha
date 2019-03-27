package com.example.miaosha.algorithm.array;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/20
 * @Des
 **/
public class TestArray {

    public static void main(String[] args) {
        //创建一个数组
        int[] arr1 = new int[3];
        //获取数组长度
        int length = arr1.length;
        System.out.println("arr1` length = " + length);
        int element0 = arr1[0];
        System.out.println("element0 = " + element0);
        arr1[0] = 99;
        System.out.println("element0 = " + arr1[0]);
        arr1[1] = 98;
        arr1[2] = 97;
        for (int i = 0; i < length; i++) {
            System.out.println("arr1 element" + i + ":" + arr1[i]);
        }
        //创建的同时 赋值
        int[] arr2 = new int[]{90,80,70,60,50};
        System.out.println("arr2` length = " + arr2.length);

    }


}

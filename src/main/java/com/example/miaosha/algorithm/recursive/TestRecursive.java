package com.example.miaosha.algorithm.recursive;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/27
 * @Des 测试递归
 **/
public class TestRecursive {

    public static void main(String[] args) {
//        print(10);
    }

    public static void print(int i){
        if (i > 0){
            System.out.println(i);
            print(i-1);
        }
    }
}

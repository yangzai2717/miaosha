package com.example.miaosha.algorithm.recursive;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/27
 * @Des 斐波那契数列
 **/
public class TestFebonacci {

    public static void main(String[] args) {
        // 1 1 2 3 5 8
        System.out.println(febonacci(6));
    }

    public static int febonacci(int n){
        if (n == 1 || n == 2){
            return 1;
        }else {
            return febonacci(n-1) + febonacci(n -2);
        }
    }

}

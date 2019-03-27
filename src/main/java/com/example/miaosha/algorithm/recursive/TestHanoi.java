package com.example.miaosha.algorithm.recursive;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/27
 * @Des 汉诺塔问题
 **/
public class TestHanoi {

    public static int count = 0;

    public static void main(String[] args) {
        hanoi(3, 'A', 'B', 'c');
        System.out.println(count);
    }

    /**
     *
     * @param n 共有几个盘子
     * @param from 开始柱子
     * @param in   中间柱子
     * @param to   结束柱子
     */
    public static void  hanoi(int n, char from, char in, char to){
        count+=1;
        if (n == 1){
            System.out.println("第1个盘子从" + from + "移到" + to);
        } else {
            hanoi(n-1, from, to, in);
            System.out.println("第"+ n +"个盘子从" + from + "移到" + to);
            hanoi(n-1, in, from, to);
        }
    }
}

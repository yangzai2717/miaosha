package com.example.miaosha.algorithm.array;

import java.util.Arrays;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/20
 * @Des
 **/
public class TestOpArray {

    public static void main(String[] args) {
        //解决数组的长度不可变的问题
        //新声明一个数组即可
        int[] array = new int[]{9,8,7};
        //快速查看元素
        System.out.println(Arrays.toString(array));
        //要加入的新元素
        int dst = 6;

        int[] array1 = new int[array.length +1];
        for (int i = 0; i < array.length; i++) {
            array1[i] = array[i];
        }
        array1[array.length] = dst;
        System.out.println(Arrays.toString(array1));
        array = array1;
        System.out.println(Arrays.toString(array));

    }


}

package com.example.miaosha.algorithm.array;

import java.util.Arrays;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/20
 * @Des
 **/
public class TestOpArray2 {

    public static void main(String[] args) {

        int[] array = new int[]{9,8,7,6,5,4};
        //快速查看元素
        System.out.println(Arrays.toString(array));
        //要删除的下标
        int dst = 5;
        int[] newArray = new int[array.length-1];
        for (int i = 0; i < newArray.length; i++) {
            if (i < dst){
                newArray[i] = array[i];
            }else {
                newArray[i] = array[i + 1];
            }
        }
        array = newArray;
        System.out.println(Arrays.toString(array));
    }


}

package com.example.miaosha.algorithm.sort;

import java.util.Arrays;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/29
 * @Des 插入算法
 **/
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = new int[]{5,7,2,9,4,1,0,5,7};
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void insertSort(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i-1]){
                int temp = arr[i];
                int j ;
                for (j = i-1; j >= 0 && temp < arr[j] ; j--) {
                    arr[j+1] = arr[j];
                }
                arr[j+1] = temp;
            }
        }
    }
}

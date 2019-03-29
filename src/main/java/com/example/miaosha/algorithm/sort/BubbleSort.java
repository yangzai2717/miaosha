package com.example.miaosha.algorithm.sort;

import java.util.Arrays;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/28
 * @Des 冒泡排序
 **/
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = new int[]{5,7,2,9,4,1,0,5,7};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void bubbleSort(int[] arr){
        //一共几轮
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length-1-i; j++) {
                if (arr[j] > arr[j+1]){
                    int temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}

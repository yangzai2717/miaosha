package com.example.miaosha.algorithm.sort;

import java.util.Arrays;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/28
 * @Des 快速排序
 **/
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = new int[]{5,7,2,9,4,1,0,5,7};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int start, int end){
        if (start < end){
            //标准数
            int stard = arr[start];
            //记录需要排序的下标
            int low = start;
            int high = end;
            while (low < high){
                while (low < high && stard <= arr[high]){
                    high--;
                }
                arr[low] = arr[high];
                while (low < high && arr[low] <= stard){
                    low++;
                }
                arr[high] = arr[low];
            }
            arr[low] = stard;
            quickSort(arr, start, low);
            quickSort(arr, low+1, end);
        }
    }
}

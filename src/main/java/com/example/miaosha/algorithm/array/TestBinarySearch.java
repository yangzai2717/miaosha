package com.example.miaosha.algorithm.array;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/27
 * @Des 二分查找算法
 **/
public class TestBinarySearch {

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5,6,7,8,9};
        int target = 8;
        int index = -1;
        //记录开始 和 结束位置 中间位置
        int begin = 0;
        int end = arr.length - 1;
        int mid = (begin + end)/2;
        while (true){
            if (arr[mid] == target){
                index = mid;
                break;
            }else {
                if (arr[mid] > target){
                    end = mid - 1;
                }else {
                    begin = mid + 1;
                }
                mid = (begin+end)/2;
            }
        }
        System.out.println(index);
    }
}

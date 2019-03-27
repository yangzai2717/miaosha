package com.example.miaosha.algorithm.array;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/27
 * @Des 线性查找
 **/
public class TestSearch {

    public static void main(String[] args) {
        int[] arr = new int[]{2,3,5,6,8,4,9,0};
        int target = 80;
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target){
                index = i;
                break;
            }
        }
        System.out.println(index);
    }
}

package com.example.miaosha.algorithm.array.demo1.util;

import java.util.Arrays;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/25
 * @Des   面向对象的数组
 **/
public class MyArray {

    private int[] elements;

    public MyArray(){
        elements = new int[0];
    }

    //返回数组长度
    public int size(){
        return elements.length;
    }

    //增加元素
    public void add(int element){
        int[] newArray = new int[elements.length+1];
        for (int i = 0; i < elements.length; i++) {
            newArray[i] = elements[i];
        }
        newArray[elements.length] = element;
        elements = newArray;
    }

    //输出所有元素
    public void show(){
        System.out.println(Arrays.toString(elements));
    }

    //根据下标删除元素
    public void del(int index){
        if (index < 0 || index > elements.length-1){
            throw new RuntimeException("下标越界");
        }
        int[] newArray = new int[elements.length-1];
        for (int i = 0; i < newArray.length; i++) {
            if (i < index){
                newArray[i] = elements[i];
            }else {
                newArray[i] = elements[i+1];
            }
        }
        elements = newArray;
    }

    //根据下标获取元素
    public int get(int index){
        if (index < 0 || index > elements.length-1){
            throw new RuntimeException("下标越界");
        }
        return elements[index];
    }

    //插入元素
    public void insert(int element, int index){
        if (index < 0 || index > elements.length - 1){
            throw new RuntimeException("下标越界");
        }
        int[] newArray = new int[elements.length+1];
        for (int i = 0; i < elements.length; i++) {
            if (i < index){
                newArray[i] = elements[i];
            }else {
                newArray[i+1] = elements[i];
            }
        }
        newArray[index] = element;
        elements = newArray;
    }

    //替换
    public void set(int index, int element){
        if (index < 0 || index > elements.length - 1){
            throw new RuntimeException("下标越界");
        }
        elements[index] = element;
    }

    //线性查找
    public int search(int target){
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == target){
                return i;
            }
        }
        return -1;
    }

    //二分法查找
    public int binarySearch(int target){
        int begin = 0;
        int end = elements.length - 1;
        int mid = (begin + end)/2;
        while (begin < end){
            if (elements[mid] == target){
                return mid;
            }else {
                if (elements[mid] > target){
                    end = mid - 1;
                }else {
                    begin = mid + 1;
                }
                mid = (begin+end)/2;
            }
        }
        return -1;
    }

}

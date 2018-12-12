package com.example.miaosha.test.datastructure.array;

import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2018/12/12
 * @Des 自定义数组类
 **/
public class Array {

    private int[] data;
    private int size = 0;

    public Array(int capacity){
        data = new int[capacity];
    }

    public Array(){
        data = new int[20];
    }

    public boolean contains(int e){
        for (int i = 0; i < size; i++){
            if (data[i] == e){
                return true;
            }
        }
        return false;
    }

    public int add(int e){
        data[size] = e;
        size++;
        return size;
    }

    public void addIndex(int index, int e){
        if (index < 0 || index > size) {
            throw new RuntimeException("下表越界");
        }
        for (int i = index; i < size; i++){
            data[i+1] = data[i];
        }
        size++;
    }

    public void out(){
        for (int i = 0; i < size; i++){
            System.out.println(data[i]);
        }
    }
}

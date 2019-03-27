package com.example.miaosha.algorithm.array.demo2;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/27
 * @Des 栈
 **/
public class MyStack {

    //栈的底层我们使用数组来存储数据
    int[] elements;

    public MyStack() {
        elements = new int[0];
    }

    //压入元素
    public void push(int element){
        int[] newArray = new int[elements.length+1];
        for (int i = 0; i < elements.length; i++) {
            newArray[i] = elements[i];
        }
        newArray[elements.length] = element;
        elements = newArray;
    }
    //取出元素
    public int pop(){
        if (elements.length == 0){
            throw new RuntimeException("stack is empty");
        }
        int element = elements[elements.length - 1];
        int[] newArr = new int[elements.length - 1];
        for (int i = 0; i < elements.length - 1; i++) {
            newArr[i] = elements[i];
        }
        elements = newArr;
        return element;
    }

    //查看栈顶元素
    public int peek(){
        if (elements.length == 0){
            throw new RuntimeException("stack is empty");
        }
        return elements[elements.length - 1];
    }

    //栈是否为空
    public boolean isEmpty(){
        return elements.length == 0;
    }
}

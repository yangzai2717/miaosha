package com.example.miaosha.algorithm.array.demo1;

import com.example.miaosha.algorithm.array.demo1.util.MyArray;
import com.example.miaosha.algorithm.array.demo2.MyQueue;
import com.example.miaosha.algorithm.array.demo2.MyStack;
import com.example.miaosha.algorithm.array.demo3.DoubleNode;
import com.example.miaosha.algorithm.array.demo3.LoopNode;
import com.example.miaosha.algorithm.array.demo3.Node;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/25
 * @Des
 **/
public class ss {

    public static void main(String[] args) {
        DoubleNode n1 = new DoubleNode(1);
        DoubleNode n2 = new DoubleNode(2);
        DoubleNode n3 = new DoubleNode(3);
        n1.after(n2);
        n2.after(n3);
        System.out.println(n2.pre().getData());
        System.out.println(n2.getData());
        System.out.println(n2.next().getData());
        System.out.println(n3.next().getData());
        System.out.println(n1.pre().getData());
    }
}

package com.example.miaosha.algorithm.array.demo3;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/27
 * @Des 双向循环链表
 **/
public class DoubleNode {

    //上一个节点
    DoubleNode pre = this;

    //下一个节点
    DoubleNode next = this;

    int data;

    public DoubleNode(int data) {
        this.data = data;
    }

    //增节点
    public void after(DoubleNode node){
        //原来的下一个节点
        DoubleNode nextNode = this.next;
        this.next = node;
        node.pre = this;

        node.next = nextNode;
        nextNode.pre = node;
    }

    //下一个节点
    public DoubleNode next(){
        return this.next;
    }

    public DoubleNode pre(){
        return this.pre;
    }

    public int getData(){
        return this.data;
    }
}

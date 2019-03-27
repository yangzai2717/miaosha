package com.example.miaosha.algorithm.array.demo3;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/27
 * @Des 循环链表
 **/
public class LoopNode {

    //节点内容
    private int data;
    //下一个节点
    private LoopNode next = this;

    public LoopNode(int data) {
        this.data = data;
    }

    //获取下一个节点
    public LoopNode next(){
        return this.next;
    }

    //获取节点数据
    public int getData(){
        return this.data;
    }


    //删除下一个节点
    public void removeNext(){
        LoopNode newNext = this.next.next;
        this.next = newNext;
    }

    //插入一个节点，作为当前节点的下一个节点
    public void after(LoopNode node){
        LoopNode nextNode = this.next;
        this.next = node;
        node.next = nextNode;
    }

}

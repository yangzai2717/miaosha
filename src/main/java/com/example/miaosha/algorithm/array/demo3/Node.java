package com.example.miaosha.algorithm.array.demo3;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/27
 * @Des 一个节点
 **/
public class Node {

    //节点内容
    private int data;
    //下一个节点
    private Node next;

    public Node(int data) {
        this.data = data;
    }

    //为节点追加节点
    public Node append(Node node){
        //当前节点
        Node currentNode = this;
        //循环像后找
        while (true){
            //取出下一个节点
            Node nextNode = currentNode.next;
            //如果下一个节点为null，当前节点已经是最后一个节点
            if (nextNode == null){
                break;
            }
            currentNode = nextNode;
        }
        currentNode.next = node;
        return this;
    }

    //获取下一个节点
    public Node next(){
        return this.next;
    }

    //获取节点数据
    public int getData(){
        return this.data;
    }

    //当前节点是不是最后一个节点
    public boolean isLast(){
        return next == null;
    }

    //删除下一个节点
    public void removeNext(){
        Node newNext = this.next.next;
        this.next = newNext;
    }

    //插入一个节点，作为当前节点的下一个节点
    public void after(Node node){
        Node nextNode = this.next;
        this.next = node;
        node.next = nextNode;
    }

    //显示所有节点信息
    public void show(){
        Node currentNode = this;
        while (true){
            System.out.println(currentNode.data + " ");
            //取出下一个节点
            currentNode = currentNode.next;
            if (currentNode == null){
                break;
            }
        }
        System.out.println();
    }
}

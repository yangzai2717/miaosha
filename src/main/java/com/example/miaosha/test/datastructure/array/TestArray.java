package com.example.miaosha.test.datastructure.array;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2018/12/12
 * @Des
 **/
public class TestArray {

    public static void main(String[] args) {
        Array array = new Array(16);
        for (int i = 0; i < 10; i++){
            array.add(i);
        }
        array.out();
        array.addIndex(3, 100);
        array.out();
        //System.out.println(array.contains(3));
    }

}

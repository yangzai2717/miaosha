package com.example.miaosha.test.sampleSuanfa;

import java.util.Arrays;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2018/12/17
 * @Des
 **/
public class MaxAndMin {

    public static void main(String[] args) {
//        int [] data = {2,4,1,6};
//        System.out.println(max(data));
        String[] data = {"A","A","B","C","C","D","D"};
        //System.out.println(Arrays.toString(unique(data)));
        //System.out.println(Arrays.toString(uniqueOne(data)));
        String [] test = {};
        //test[0] = "A";
        //System.out.println(Arrays.toString(unique(data)));
        String ss = "abdcadfga";
        String s = "ab";
        //System.out.println(ss.substring(1,3));
        String a = "a";
    }

    public static int max(int [] data){
        int max = data[0];
       for (int i = 1; i < data.length; i++){
           if (data[i] > max){
               max = data[i];
           }
       }
       return max;
    }

    //data必须是已经排序的
    public static String[] uniqueOne(String[] data){
        String temp= "";
        String[] u = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            if (i == 0){
                u[0] = data[0];
                temp = data[0];
            }else{
                if (temp != data[i]){
                    u[i] = data[i];
                    temp = data[i];
                }
            }
        }
        return u;
    }

    public static String[] unique(String[] data){
        String[] u = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            if (exist(data[i], u)){
                continue;
            }else {
                u[i] = data[i];
            }
        }
        return u;
    }

    public static boolean exist(String s, String[] data){
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            if (s == data[i]){
                return true;
            }else {
                count++;
            }
        }
        if (count == data.length){
            return false;
        }
        return true;
    }
}

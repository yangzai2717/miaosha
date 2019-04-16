package com.example.miaosha.test.sampleSuanfa;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/4/15
 * @Des leetcode 最长公共前缀 14
 **/
public class LongestPrefix {

    public static void main(String[] args) {
        String[] str = new String[]{"abccccc","abddddd","abeeeee"};
        System.out.println(longestPrefix(str));
    }

    public static String longestPrefix(String[] strs){
        if (strs.length == 0){
            return "";
        }
        int i = 0;
        String prefix = "";
        String result;
        boolean flag = false;
        while (true){
            i++;
            result = prefix;
            if (i > strs[0].length()){
                break;
            }
            prefix = strs[0].substring(0,i);
            for (String str : strs){
                if (i > str.length() || !str.startsWith(prefix)){
                    flag = true;
                    break;
                }
            }
            if (flag){
                break;
            }
        }
        return result;
    }
}

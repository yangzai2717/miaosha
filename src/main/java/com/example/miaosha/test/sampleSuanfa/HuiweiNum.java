package com.example.miaosha.test.sampleSuanfa;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/29
 * @Des 回文数
 **/
public class HuiweiNum {

    public static void main(String[] args) {
        System.out.println(isHuiwen(10));
    }

    public static boolean isHuiwen(int x){
        if (x < 0 || x%10 == 0){
            return false;
        }else if (x /10 == 0){
            return true;
        }
        int result = 0;
        //只需要比较一半就可以
        while (x > result){
            int  digit = x % 10;
            result = result * 10 + digit;
            x = x/10;
        }
        return (x == result || x / 10 == result);
    }
}

package com.example.miaosha.test.sampleSuanfa;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/4/1
 * @Des leetcode 13
 **/
public class RomanToInt {

    public static void main(String[] args) {
        System.out.println(romToInt("MCMXCIV"));

    }

    public static int romToInt(String rom){
        Map<Character, Integer> romMap = new HashMap<>();
        romMap.put('I', 1);
        romMap.put('V', 5);
        romMap.put('X', 10);
        romMap.put('L', 50);
        romMap.put('C', 100);
        romMap.put('D', 500);
        romMap.put('M', 1000);
        int sum = 0;
        for (int i = 0; i < rom.length()-1; i++) {
            if (romMap.get(rom.charAt(i)) <  romMap.get(rom.charAt(i+1))){
                sum -= romMap.get(rom.charAt(i));
            }else {
                sum += romMap.get(rom.charAt(i));
            }
        }
        sum += romMap.get(rom.charAt(rom.length()-1));
        return sum;
    }
}

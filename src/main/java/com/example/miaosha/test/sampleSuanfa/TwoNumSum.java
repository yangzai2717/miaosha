package com.example.miaosha.test.sampleSuanfa;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author pangyy
 * @Version 1.0
 * @Date 2019/3/28
 * @Des leetcode 1 两数之和 利用数组 和 哈希表
 **/
public class TwoNumSum {

    public static void main(String[] args) {
        int[] nums = new int[]{2,7,8,11};
        int[] result = sum(nums, 9);
        System.out.println(Arrays.toString(result));
    }

    public static int[] sum(int[] nums, int targer){
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int temp = targer - nums[i];
            if (map.containsKey(temp)){
                return new int[]{map.get(temp), i};
            }else {
                map.put(nums[i], i);
            }
        }
        return new int[0];
    }
}

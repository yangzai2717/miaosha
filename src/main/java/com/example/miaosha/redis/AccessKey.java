package com.example.miaosha.redis;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/30 15:00
 * @Description:
 */
public class AccessKey extends BasePrefix{

    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }

    public static AccessKey access = new AccessKey(5,"access");
}

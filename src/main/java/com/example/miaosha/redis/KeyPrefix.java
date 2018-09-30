package com.example.miaosha.redis;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/30 14:22
 * @Description: redis缓存 key的前缀，接口——抽象类——实现类（模板模式）
 */
public interface KeyPrefix {

    //有效期
    public int expireSeconds();

    //前缀
    public String getPrefix();
}

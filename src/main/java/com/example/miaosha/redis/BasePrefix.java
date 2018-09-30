package com.example.miaosha.redis;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/30 14:38
 * @Description:
 */
public abstract class BasePrefix implements KeyPrefix{

    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix){
        this.expireSeconds = 0;
        this.prefix = prefix;
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = this.getClass().getSimpleName();
        return className + ":" +prefix;
    }
}

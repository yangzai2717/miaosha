package com.example.miaosha.access;

import com.example.miaosha.domain.MiaoshaUser;

public class UserContext {

    //ThreadLocal 多线程下保证线程安全  他是与当前线程绑定的，往ThreadLocal里面放东西 是放到当前线程里面来，
    //如果是多线程下，他们之间是不存在冲突的，不会存在线程安全问题
    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();

    public static void setUser(MiaoshaUser user){
        userHolder.set(user);
    }

    public static MiaoshaUser getUser(){
        return userHolder.get();
    }
}

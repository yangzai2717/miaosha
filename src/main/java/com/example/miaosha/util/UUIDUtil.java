package com.example.miaosha.util;

import java.util.UUID;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/8 14:08
 * @Description:
 */
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-",""); //原生的uuid是代有-  这个符号的，我们想把他去掉
    }
}

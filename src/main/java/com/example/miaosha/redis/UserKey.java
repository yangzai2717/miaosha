package com.example.miaosha.redis;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/30 15:00
 * @Description:
 */
public class UserKey extends BasePrefix{

    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}

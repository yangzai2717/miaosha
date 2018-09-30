package com.example.miaosha.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/30 10:35
 * @Description:
 */
@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;


    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
            //因为是一个连接池，所以要关闭连接，要不就会导致连接不够用了
        }
    }

    public <T> boolean set(KeyPrefix prefix, String key, T value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if(str == null || str.length() <= 0){
                return false;
            }
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            jedis.set(realKey, str);
            return true;
        } finally {
            returnToPool(jedis);
            //因为是一个连接池，所以要关闭连接，要不就会导致连接不够用了
        }
    }

    public <T> boolean exists(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
            //因为是一个连接池，所以要关闭连接，要不就会导致连接不够用了
        }
    }

    private <T> String beanToString(T value) {
        if (value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class){
            return ""+value;
        } else if (clazz == long.class || clazz == Long.class){
            return ""+value;
        } else if(clazz == String.class){
            return (String)value;
        } else {
            return JSON.toJSONString(value);
        }

    }

    private <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null){
            return null;
        }
        if(clazz == int.class || clazz == Integer.class){
            return (T) Integer.valueOf(str);
        } else if (clazz == long.class || clazz == Long.class){
            return (T) Long.valueOf(str);
        } else if(clazz == String.class){
            return (T) str;
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }


    private void returnToPool(Jedis jedis) {
        if(jedis != null){
            //并不是把其销毁，而是返回到了连接池当中
            jedis.close();
        }
    }
}

package com.example.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/30 11:21
 * @Description:
 */
@Service
public class RedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;


    @Bean
    public JedisPool jedisPoolFactory(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
                redisConfig.getTimeout() * 1000, redisConfig.getPassword(), 0);
        return jp;
    }

    //测试
    public static void main(String[] args) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        JedisPool jp = new JedisPool(poolConfig,"localhost", 6379,
                2 * 1000, null, 0);
        Jedis jedis = jp.getResource();
        System.out.println(jedis.get("name"));
    }

}

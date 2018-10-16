package com.example.miaosha.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/16 17:52
 * @Description:
 */
@Configuration
public class MQConfig {

    public static final String QUEUE = "queue";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE, true); //queue名称，是否持久化
    }


}

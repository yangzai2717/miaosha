package com.example.miaosha.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String ROUTING_KEY1 = "topic.key1";
    public static final String ROUTING_KEY2 = "topic.#";
    /**
     * Direct模式
     * @param
     */
    @Bean
    public Queue queue(){
        return new Queue(QUEUE, true); //queue名称，是否持久化
    }

    /**
     * Topic模式  exchange交换机
     * @param
     */
    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE1, true); //queue名称，是否持久化
    }
    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPIC_QUEUE2, true); //queue名称，是否持久化
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }
    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }
    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }

}

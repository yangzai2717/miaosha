package com.example.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/16 17:51
 * @Description: mq接受者
 */
@Service
public class MQReceiver {

    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);


    @RabbitListener(queues = MQConfig.QUEUE) //添加监听
    public void receive(String message){
        logger.info("receive message : ", message);
    }


    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1) //添加监听
    public void receiveTopic1(String message){
        logger.info("topic queue1 message : ", message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2) //添加监听
    public void receiveTopic2(String message){
        logger.info("topic queue2 message : ", message);
    }

}

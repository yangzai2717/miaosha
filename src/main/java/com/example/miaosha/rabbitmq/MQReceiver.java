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

}

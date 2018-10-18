package com.example.miaosha.mq;

import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/18 16:25
 * @Description:
 */
@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    final ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback(){

        @Override
        public void confirm(CorrelationData correlationData, boolean b, String s) {
            System.out.println("correlationData: " + correlationData);
            System.out.println("ack: " + b);
            if(!b){
                System.out.println("异常处理");
            }
        }
    };


    final ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback(){

        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
                                    String exchange, String routingkey) {
            System.out.println("return exchange: " + exchange + "return routingkey: " + routingkey + "replyText: " + replyText);

        }
    };

    public void send(Object message, Map<String, Object> properties) throws Exception{
        MessageHeaders mhs = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, mhs);
        rabbitTemplate.setConfirmCallback(confirmCallback); //消息的确认
        rabbitTemplate.setReturnCallback(returnCallback);  //消息的返回
        CorrelationData cd = new CorrelationData("123456789");
        //cd.setId(UUID.randomUUID().toString() + String.valueOf(new Date().toString())); //确保全局唯一
        rabbitTemplate.convertAndSend("exchange-1", "springboot.hello", msg, cd);
    }



}

package com.example.miaosha.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/18 19:42
 * @Description:
 */
@Component
public class RabbitReceiver {


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-1",
                    durable = "true"),
            exchange = @Exchange(value = "exchange-1",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "springboot.*"
    ))
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception{
        System.out.println("消费端：Payload " + message.getPayload());
        System.out.println("消费端： " + message.getPayload());
        Long deliveryTay = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        //false 不批量
        channel.basicAck(deliveryTay, false);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
                    durable = "${spring.rabbitmq.listener.order.queue.durable}"),
            exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
                    type = "${spring.rabbitmq.listener.order.exchange.type}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
            key = "${spring.rabbitmq.listener.order.key}"
    ))
    @RabbitHandler
    public void onOrderMessage(@Payload com.example.miaosha.mq.entity.Order order, Channel channel,
                               @Headers Map<String, Object> headers) throws Exception{
        System.out.println("消费端：order " + order.getId());
        Long deliveryTay = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        //false 不批量 手动ack
        channel.basicAck(deliveryTay, false);
    }


}

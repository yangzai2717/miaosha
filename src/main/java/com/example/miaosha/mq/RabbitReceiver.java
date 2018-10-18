package com.example.miaosha.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/18 19:42
 * @Description:
 */
@Component
public class RabbitReceiver {


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
    public void onMessage(Message message, Channel channel) throws Exception{
        System.out.println("消费端：Payload " + message.getPayload());
        System.out.println("消费端： " + message.getPayload());
        Long deliveryTay = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        //false 不批量
        channel.basicAck(deliveryTay, false);
    }
}

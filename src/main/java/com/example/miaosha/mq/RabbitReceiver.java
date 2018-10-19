package com.example.miaosha.mq;

import com.example.miaosha.domain.MiaoshaOrder;
import com.example.miaosha.domain.MiaoshaUser;
import com.example.miaosha.service.GoodsService;
import com.example.miaosha.service.MiaoshaService;
import com.example.miaosha.service.OrderService;
import com.example.miaosha.vo.GoodsVo;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
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

    public static final Logger logger = LoggerFactory.getLogger(RabbitReceiver.class);

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

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

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-miaosha",
                    durable = "true"),
            exchange = @Exchange(value = "exchange-miaosha",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "miaosha.*"
    ))
    @RabbitHandler
    public void onMiaoshaMessage(@Payload com.example.miaosha.rabbitmq.MiaoshaMessage miaoshaMessage, Channel channel,
                                 @Headers Map<String, Object> headers) throws Exception{
        logger.info("消费端：秒杀message " + miaoshaMessage.toString());


        //业务代码
        MiaoshaUser user = miaoshaMessage.getUser();
        long goodsId = miaoshaMessage.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user, goods);

        logger.info("-------消费端 消费完毕 完成减 库存----- ");

        Long deliveryTay = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        //false 不批量 手动ack
        channel.basicAck(deliveryTay, false);
    }

}

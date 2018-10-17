package com.example.miaosha.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/17 09:47
 * @Description: 消费者
 */
public class Consumer {


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //1 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.46.201");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        //2 创建链接
        Connection connection = connectionFactory.newConnection();


        //3 通过连接创建channel
        Channel channel = connection.createChannel();

        //4 声明一个 队列
        String queueName = "test001";
        //1 队列名字  2，是否持久化 3，是否唯一的channel所有 4是否自动删除
        channel.queueDeclare(queueName, true, false, false, null);

        //5 创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //6 设置channel  , 自动签收 ,
        channel.basicConsume(queueName, true, queueingConsumer);

        //7 获取消息
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端： " + msg);
            //Envelope envelope = delivery.getEnvelope();//
        }


    }

}

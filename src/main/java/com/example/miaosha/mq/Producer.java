package com.example.miaosha.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/17 10:02
 * @Description: 生产者
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.46.201");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //创建链接
        Connection connection = connectionFactory.newConnection();


        //通过连接创建channel
        Channel channel = connection.createChannel();

        //通过channel发送数据
        for (int i=0; i< 5; i++){

            String msg = "hello";
            //1 exchange 2routing key
            channel.basicPublish("", "test001", null, msg.getBytes());
        }

        //关闭连接
        channel.close();
        connection.close();
    }
}

package com.example.miaosha.mq;

import com.example.miaosha.mq.adapter.MessageDelegate;
import com.example.miaosha.mq.converter.ImageMessageConverter;
import com.example.miaosha.mq.converter.PDFMessageConverter;
import com.example.miaosha.mq.converter.TextMessageConverter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/17 20:42
 * @Description:
 */
@Configuration
@ConfigurationProperties()
@ComponentScan({"com.example.miaosha.*"})
public class RabbitMQConfig1 {



}

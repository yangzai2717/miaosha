package com.example.miaosha;

import com.example.miaosha.mq.RabbitSender;
import com.example.miaosha.mq.entity.Order;
import com.example.miaosha.mq.entity.Packaged;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.rngom.binary.DataExceptPattern;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/18 10:18
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMQ {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitSender rabbitSender;

    @Test
    public void testAdmin() throws Exception {
        rabbitAdmin.declareExchange(new DirectExchange("test.direct", false, false));

        rabbitAdmin.declareExchange(new TopicExchange("test.topic", false, false));

        rabbitAdmin.declareExchange(new FanoutExchange("test.fanout", false, false));

        rabbitAdmin.declareQueue(new Queue("test.direct.queue", false));

        rabbitAdmin.declareQueue(new Queue("test.topic.queue", false));

        rabbitAdmin.declareQueue(new Queue("test.fanout.queue", false));

        rabbitAdmin.declareBinding(new Binding("test.direct.queue",
                Binding.DestinationType.QUEUE,
                "test.direct", "direct", new HashMap<String, Object>()));

        rabbitAdmin.declareBinding(
                BindingBuilder
                        .bind(new Queue("test.topic.queue", false))		//直接创建队列
                        .to(new TopicExchange("test.topic", false, false))	//直接创建交换机 建立关联关系
                        .with("user.#"));	//指定路由Key


        rabbitAdmin.declareBinding(
                BindingBuilder
                        .bind(new Queue("test.fanout.queue", false))
                        .to(new FanoutExchange("test.fanout", false, false)));

        //清空队列数据
        rabbitAdmin.purgeQueue("test.topic.queue", false);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMesssage() throws Exception{
        //创建消息
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc", "信息描述");
        messageProperties.getHeaders().put("type", "自定义消息提");
        Message message = new Message( "hello mq".getBytes(),messageProperties);

        //发送消息
        rabbitTemplate.convertAndSend("topic001", "spring.amqp", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                System.out.println("----添加额外的设置-----");
                message.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
                message.getMessageProperties().getHeaders().put("attr", "额外新加的属性");
                return message;
            }
        });
    }

    @Test
    public void testSendMesssage2() throws Exception{
        //创建消息
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");
        Message message = new Message( "hello mq 消息123".getBytes(),messageProperties);
        rabbitTemplate.convertAndSend("topic001", "spring.abc", message);

        //发送消息
        rabbitTemplate.convertAndSend("topic001", "spring.amqp", "hello  send 1");
        rabbitTemplate.convertAndSend("topic002", "rabbit.abc", "hello  send 2");
    }

    @Test
    public void testSendMesssage3Text() throws Exception{
        //创建消息
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");
        Message message = new Message( "hello mq 消息123".getBytes(),messageProperties);
        rabbitTemplate.convertAndSend("topic001", "spring.abc", message + "1");
        rabbitTemplate.convertAndSend("topic002", "rabbit.abc", message + "2");

    }

    @Test
    public void testSendJsonMessage() throws Exception {

        Order order = new Order();
        order.setId("001");
        order.setName("消息订单");
        order.setContent("描述信息");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(order);
        System.err.println("order 4 json: " + json);

        MessageProperties messageProperties = new MessageProperties();
        //这里注意一定要修改contentType为 application/json
        messageProperties.setContentType("application/json");
        Message message = new Message(json.getBytes(), messageProperties);

        rabbitTemplate.send("topic001", "spring.order", message);
    }


    @Test
    public void testSendJavaMessage() throws Exception {

        Order order = new Order();
        order.setId("001");
        order.setName("订单消息");
        order.setContent("订单描述信息");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(order);
        System.err.println("order 4 json: " + json);

        MessageProperties messageProperties = new MessageProperties();
        //这里注意一定要修改contentType为 application/json
        messageProperties.setContentType("application/json");
        messageProperties.getHeaders().put("__TypeId__", "com.example.miaosha.mq.entity.Order");
        Message message = new Message(json.getBytes(), messageProperties);

        rabbitTemplate.send("topic001", "spring.order", message);
    }

    @Test
    public void testSendMappingMessage() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Order order = new Order();
        order.setId("001");
        order.setName("订单消息");
        order.setContent("订单描述信息");

        String json1 = mapper.writeValueAsString(order);
        System.err.println("order 4 json: " + json1);

        MessageProperties messageProperties1 = new MessageProperties();
        //这里注意一定要修改contentType为 application/json
        messageProperties1.setContentType("application/json");
        messageProperties1.getHeaders().put("__TypeId__", "order");
        Message message1 = new Message(json1.getBytes(), messageProperties1);
        rabbitTemplate.send("topic001", "spring.order", message1);

        Packaged pack = new Packaged();
        pack.setId("002");
        pack.setName("包裹消息");
        pack.setDescription("包裹描述信息");

        String json2 = mapper.writeValueAsString(pack);
        System.err.println("pack 4 json: " + json2);

        MessageProperties messageProperties2 = new MessageProperties();
        //这里注意一定要修改contentType为 application/json
        messageProperties2.setContentType("application/json");
        messageProperties2.getHeaders().put("__TypeId__", "packaged");
        Message message2 = new Message(json2.getBytes(), messageProperties2);
        rabbitTemplate.send("topic001", "spring.pack", message2);
    }

    @Test
    public void testSendExtConverterMessage() throws Exception {
			byte[] body = Files.readAllBytes(Paths.get("d:/002_books", "picture.png"));
			MessageProperties messageProperties = new MessageProperties();
			messageProperties.setContentType("image/png");
			messageProperties.getHeaders().put("extName", "png");
			Message message = new Message(body, messageProperties);
			rabbitTemplate.send("", "image_queue", message);

//        byte[] body = Files.readAllBytes(Paths.get("d:/002_books", "mysql.pdf"));
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setContentType("application/pdf");
//        Message message = new Message(body, messageProperties);
//        rabbitTemplate.send("", "pdf_queue", message);
    }

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testSender1() throws Exception{
        Map<String, Object> properties  =new HashMap<String, Object>();
        properties.put("number", "12345");
        properties.put("send_time", simpleDateFormat.format(new Date()));
        rabbitSender.send("hello pyy ", properties);
    }

    @Test
    public void testSender2() throws Exception{
        Order order = new Order();
        order.setId("001");
        order.setName("order");
        rabbitSender.sendOrder(order);
    }

}

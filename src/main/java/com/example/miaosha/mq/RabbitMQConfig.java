//package com.example.miaosha.mq;
//
//import com.example.miaosha.mq.adapter.MessageDelegate;
//import com.example.miaosha.mq.converter.ImageMessageConverter;
//import com.example.miaosha.mq.converter.PDFMessageConverter;
//import com.example.miaosha.mq.converter.TextMessageConverter;
//import com.rabbitmq.client.Channel;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.amqp.support.ConsumerTagStrategy;
//import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
//import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * @Auther: 庞洋洋
// * @Date: 2018/10/17 20:42
// * @Description:
// */
//
//public class RabbitMQConfig {
//
//    @Bean
//    public ConnectionFactory connectionFactory(){
//        CachingConnectionFactory connectionFactory  = new CachingConnectionFactory();
//        connectionFactory.setAddresses("192.168.46.201:5672");
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        connectionFactory.setVirtualHost("/");
//        return connectionFactory;
//    }
//
//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//        rabbitAdmin.setAutoStartup(true); //自动启动
//        return rabbitAdmin;
//    }
//
//    /**
//     * 针对消费者配置
//     * 1. 设置交换机类型
//     * 2. 将队列绑定到交换机
//     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
//     HeadersExchange ：通过添加属性key-value匹配
//     DirectExchange:按照routingkey分发到指定队列
//     TopicExchange:多关键字匹配
//     */
//    @Bean
//    public TopicExchange exchange001(){
//        return new TopicExchange("topic001", true, false);
//    }
//
//    @Bean
//    public Queue queue001(){
//        return new Queue("queue001", true); //队列持久
//    }
//
//    @Bean
//    public Binding binding001(){
//        return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
//    }
//
//    @Bean
//    public TopicExchange exchange002() {
//        return new TopicExchange("topic002", true, false);
//    }
//
//    @Bean
//    public Queue queue002() {
//        return new Queue("queue002", true); //队列持久
//    }
//
//    @Bean
//    public Binding binding002() {
//        return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
//    }
//
//    @Bean
//    public Queue queue003() {
//        return new Queue("queue003", true); //队列持久
//    }
//
//    @Bean
//    public Binding binding003() {
//        return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");
//    }
//
//    @Bean
//    public Queue queue_image() {
//        return new Queue("image_queue", true); //队列持久
//    }
//
//    @Bean
//    public Queue queue_pdf() {
//        return new Queue("pdf_queue", true); //队列持久
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory){
//        SimpleMessageListenerContainer  container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueues(queue001(), queue002(), queue003(), queue_image(), queue_pdf());
//        container.setConcurrentConsumers(1);
//        container.setMaxConcurrentConsumers(5);
//        container.setDefaultRequeueRejected(false);  //重回队列
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);  //自动签收
//        container.setExposeListenerChannel(true); //是否外露
//
//        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
//            @Override
//            public String createConsumerTag(String s) {
//                return s + "_" + UUID.randomUUID().toString();
//            }
//        });
//        /*container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                //如果有消息 会被监听，进入该方法
//                String msg = new String(message.getBody());
//                System.out.println("-----------消费者：" + msg);
//            }
//        });*/
//
//        //1.  适配器方式  默认是由自己的方法名字的，handleMessage
//        //可以自己指定一个方法的名字 ：consumeMessage
//        //也可以添加一个转换器，： 从字节数组 转换为 String
//        /*MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//        adapter.setDefaultListenerMethod("consumeMessage");
//        adapter.setMessageConverter(new TextMessageConverter());
//        container.setMessageListener(adapter);*/
//
//
//        //2.1 适配器方式： 我们的队列名称 和 方法名称也可以进行一一的匹配 使用string 转换器
//        /*MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//        adapter.setMessageConverter(new TextMessageConverter());
//        Map<String, String> queueOrTagToMethodName = new HashMap<String, String>();
//        queueOrTagToMethodName.put("queue001", "method1");
//        queueOrTagToMethodName.put("queue002", "method2");
//        adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
//        container.setMessageListener(adapter);*/
//
//
//
//        //2.2 支持json 转换器
//        /*MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//        adapter.setDefaultListenerMethod("consumeMessage");
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//        adapter.setMessageConverter(jackson2JsonMessageConverter);
//        container.setMessageListener(adapter);*/
//
//
//
//        //2.3 DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象转换
//
//        /*MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//        adapter.setDefaultListenerMethod("consumeMessage");
//
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
//        javaTypeMapper.setTrustedPackages("*");
//        jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
//        adapter.setMessageConverter(jackson2JsonMessageConverter);
//        container.setMessageListener(adapter);*/
//
//
//        //2.3 DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象多映射转换
//
//         /*MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//         adapter.setDefaultListenerMethod("consumeMessage");
//         Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//         DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
//         javaTypeMapper.setTrustedPackages("*");
//
//         Map<String, Class<?>> idClassMapping = new HashMap<String, Class<?>>();
//         idClassMapping.put("order", com.example.miaosha.mq.entity.Order.class);
//         idClassMapping.put("packaged", com.example.miaosha.mq.entity.Packaged.class);
//
//         javaTypeMapper.setIdClassMapping(idClassMapping);
//
//         jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
//         adapter.setMessageConverter(jackson2JsonMessageConverter);
//         container.setMessageListener(adapter);*/
//
//
//        //1.4 ext convert
//
//        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
//        adapter.setDefaultListenerMethod("consumeMessage");
//
//        //全局的转换器:
//        ContentTypeDelegatingMessageConverter convert = new ContentTypeDelegatingMessageConverter();
//
//        TextMessageConverter textConvert = new TextMessageConverter();
//        convert.addDelegate("text", textConvert);
//        convert.addDelegate("html/text", textConvert);
//        convert.addDelegate("xml/text", textConvert);
//        convert.addDelegate("text/plain", textConvert);
//
//        Jackson2JsonMessageConverter jsonConvert = new Jackson2JsonMessageConverter();
//        convert.addDelegate("json", jsonConvert);
//        convert.addDelegate("application/json", jsonConvert);
//
//        ImageMessageConverter imageConverter = new ImageMessageConverter();
//        convert.addDelegate("image/png", imageConverter);
//        convert.addDelegate("image", imageConverter);
//
//        PDFMessageConverter pdfConverter = new PDFMessageConverter();
//        convert.addDelegate("application/pdf", pdfConverter);
//
//
//        adapter.setMessageConverter(convert);
//        container.setMessageListener(adapter);
//
//
//
//
//
//        return container;
//    }
//
//
//}

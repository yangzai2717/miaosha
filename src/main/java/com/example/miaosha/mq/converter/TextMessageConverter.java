package com.example.miaosha.mq.converter;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/18 14:25
 * @Description:
 */
public class TextMessageConverter implements MessageConverter {

    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        //java 对象转换为 message对象
        return new Message(o.toString().getBytes(), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        //message 对象转换为 java 对象
        String contentType = message.getMessageProperties().getContentType();
        if (null != contentType && contentType.contains("text")){
            return  new String(message.getBody());
        }
        return message.getBody();
    }
}

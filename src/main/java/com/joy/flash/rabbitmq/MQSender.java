package com.joy.flash.rabbitmq;

import com.joy.flash.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SongLiang on 2019-09-19
 */
@Slf4j
@Service
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(Object message) {
        String msg = RedisService.beanToString(message);
        log.info("receive send: {}", msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }

    public void sendTopic(Object message) {
        String msg = RedisService.beanToString(message);
        log.info("send topic message: {}", msg);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg+"1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
    }

    public void sendFanout(Object message) {
        String msg = RedisService.beanToString(message);
        log.info("send fanout message: {}", msg);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg+"1");
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg+"2");
    }

    public void sendHeader(Object message) {
        String msg = RedisService.beanToString(message);
        log.info("send header message: {}", msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("header1", "value1");
        properties.setHeader("header2", "value2");
        Message obj = new Message(msg.getBytes(), properties);
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
    }

}

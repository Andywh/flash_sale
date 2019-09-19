package com.joy.flash.rabbitmq;

import com.joy.flash.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
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
}

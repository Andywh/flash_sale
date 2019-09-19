package com.joy.flash.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Created by SongLiang on 2019-09-19
 */
@Slf4j
@Service
public class MQReceiver {

    /**
     * Direct 模式 交换机 Exchange
     * @param message
     */
    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
      log.info("receive message: {}", message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info("topic queue1 message: {}", message);

    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info("topic queue2 message: {}", message);
    }

    @RabbitListener(queues = MQConfig.HEADER_QUEUE1)
    public void receiveHeaderQueue(byte[] message) {
        log.info("header queue2 message: {}", new String(message));
    }


}

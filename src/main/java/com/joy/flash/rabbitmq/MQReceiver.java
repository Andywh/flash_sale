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
     * Direct 模式
     * @param message
     */
    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
      log.info("receive message: {}", message);
    }

}

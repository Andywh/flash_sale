package com.joy.flash.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by SongLiang on 2019-09-19
 */
@Configuration
public class MQConfig {

    public static final String QUEUE = "queue";

    @Bean
    public Queue queue() {
        return new Queue(MQConfig.QUEUE, true);
    }


}

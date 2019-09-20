package com.joy.flash.rabbitmq;

import com.joy.flash.VO.GoodsVO;
import com.joy.flash.enums.ResultEnum;
import com.joy.flash.model.MiaoshaOrder;
import com.joy.flash.model.MiaoshaUser;
import com.joy.flash.model.OrderInfo;
import com.joy.flash.service.GoodsService;
import com.joy.flash.service.MiaoshaService;
import com.joy.flash.service.OrderService;
import com.joy.flash.service.RedisService;
import com.joy.flash.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SongLiang on 2019-09-19
 */
@Slf4j
@Service
public class MQReceiver {

    @Autowired
    GoodsService goodsService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    OrderService orderService;

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


    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaoshaQueue(String message) {
        log.info("receive miaosha message: {}", message);
        MiaoshaMessage mm = RedisService.stringToBean(message, MiaoshaMessage.class);
        long goodsId = mm.getGoodsId();
        MiaoshaUser user = mm.getUser();
        // 判断库存
        GoodsVO goods = goodsService.getGoodsVOByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }

        // 判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }

        // 减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }


}

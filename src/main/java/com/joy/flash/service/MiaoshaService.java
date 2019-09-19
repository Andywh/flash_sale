package com.joy.flash.service;

import com.joy.flash.VO.GoodsVO;
import com.joy.flash.dao.GoodsMapper;
import com.joy.flash.model.Goods;
import com.joy.flash.model.MiaoshaUser;
import com.joy.flash.model.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by SongLiang on 2019-09-12
 */
@Service
@Slf4j
public class MiaoshaService {

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVO goods) {
        // 减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);

        // 下订单
        // order_info miaosha_order
        return orderService.createOrder(user, goods);
    }

}

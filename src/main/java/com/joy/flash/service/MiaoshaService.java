package com.joy.flash.service;

import com.joy.flash.VO.GoodsVO;
import com.joy.flash.dao.GoodsMapper;
import com.joy.flash.model.Goods;
import com.joy.flash.model.MiaoshaOrder;
import com.joy.flash.model.MiaoshaUser;
import com.joy.flash.model.OrderInfo;
import com.joy.flash.redis.MiaoshaKey;
import com.joy.flash.utils.MD5Util;
import com.joy.flash.utils.UUIDUtil;
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

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVO goods) {
        // 减库存 下订单 写入秒杀订单
        boolean success = goodsService.reduceStock(goods);

        // 下订单
        // order_info miaosha_order

        if (success) {
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }

    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if (order != null) { // 秒杀成功
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }


    private boolean getGoodsOver(long goodsId) {
        return redisService.exist(MiaoshaKey.isGoodsOver, ""+goodsId);
    }

    public boolean checkPath(MiaoshaUser user, long goodsId, String path) {
        if (user == null || path == null) {
            return false;
        }
        String pathOld = redisService.get(MiaoshaKey.getMiaoshaPath, ""+user.getId()+"_"+goodsId, String.class);
        return path.equals(pathOld);
    }

    public String createMiaoshaPath(MiaoshaUser user, long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
        redisService.set(MiaoshaKey.getMiaoshaPath, ""+user.getId()+"_"+goodsId, str);
        return str;
    }
}

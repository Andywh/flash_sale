package com.joy.flash.controller;

import com.joy.flash.VO.GoodsVO;
import com.joy.flash.VO.ResultVO;
import com.joy.flash.enums.ResultEnum;
import com.joy.flash.model.MiaoshaOrder;
import com.joy.flash.model.MiaoshaUser;
import com.joy.flash.model.OrderInfo;
import com.joy.flash.service.*;
import com.joy.flash.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by SongLiang on 2019-09-12
 */
@Slf4j
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    @ResponseBody
    public ResultVO list(Model model, MiaoshaUser user,
                         @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return ResultVOUtil.error(ResultEnum.SESSION_ERROR);
        }
        // 判断库存
        GoodsVO goods = goodsService.getGoodsVOByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return ResultVOUtil.error(ResultEnum.MIAO_SHA_OVER);
        }

        // 判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return ResultVOUtil.error(ResultEnum.REPEATE_MIAOSHA);
        }

        // 减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        return ResultVOUtil.success(orderInfo);
    }

}

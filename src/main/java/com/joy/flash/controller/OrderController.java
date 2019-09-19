package com.joy.flash.controller;

import com.joy.flash.VO.GoodsVO;
import com.joy.flash.VO.OrderDetailVO;
import com.joy.flash.VO.ResultVO;
import com.joy.flash.enums.ResultEnum;
import com.joy.flash.model.MiaoshaUser;
import com.joy.flash.model.OrderInfo;
import com.joy.flash.service.GoodsService;
import com.joy.flash.service.OrderService;
import com.joy.flash.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;

/**
 * Created by SongLiang on 2019-09-18
 */
@Slf4j
@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public ResultVO info(Model model, MiaoshaUser user,
                         @RequestParam("orderId") long orderId) {
        log.info("order detail");
        if (user == null) {
            return ResultVOUtil.error(ResultEnum.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return ResultVOUtil.error(ResultEnum.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVO goodsVO = goodsService.getGoodsVOByGoodsId(goodsId);

        OrderDetailVO orderDetailVO = new OrderDetailVO();
        orderDetailVO.setGoods(goodsVO);
        orderDetailVO.setOrder(order);
        return ResultVOUtil.success(orderDetailVO);
    }

}

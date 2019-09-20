package com.joy.flash.controller;

import com.joy.flash.VO.GoodsVO;
import com.joy.flash.VO.ResultVO;
import com.joy.flash.access.AccessLimit;
import com.joy.flash.enums.ResultEnum;
import com.joy.flash.model.MiaoshaOrder;
import com.joy.flash.model.MiaoshaUser;
import com.joy.flash.rabbitmq.MQSender;
import com.joy.flash.rabbitmq.MiaoshaMessage;
import com.joy.flash.redis.GoodsKey;
import com.joy.flash.service.*;
import com.joy.flash.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SongLiang on 2019-09-12
 */
@Slf4j
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

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

    @Autowired
    MQSender sender;

    private Map<Long, Boolean> localOverMap = new HashMap<>();

    /**
     * 系统初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> goodsVOList = goodsService.getGoodsVOList();
        if (goodsVOList == null) {
            return;
        }
        for (GoodsVO goods : goodsVOList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }
    }

    @RequestMapping("/{path}/do_miaosha")
    @ResponseBody
    public ResultVO list(MiaoshaUser user,
                         @RequestParam("goodsId") long goodsId,
                         @PathVariable("path") String path) {
        if (user == null) {
            return ResultVOUtil.error(ResultEnum.SESSION_ERROR);
        }
        // 验证 path
        boolean check = miaoshaService.checkPath(user, goodsId, path);
        if (!check) {
            return ResultVOUtil.error(ResultEnum.REQUEST_ILLEGAL);
        }
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return ResultVOUtil.error(ResultEnum.MIAO_SHA_OVER);
        }
        // 预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, ""+goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return ResultVOUtil.error(ResultEnum.MIAO_SHA_OVER);
        }

        // 判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return ResultVOUtil.error(ResultEnum.REPEATE_MIAOSHA);
        }
        // 入队
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setUser(user);
        miaoshaMessage.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(miaoshaMessage);
        return ResultVOUtil.success(); // 排队中

        /*
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

         */
    }

    /**
     * orderId: 成功
     * -1： 秒杀失败
     * 0： 排队中
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO miaoshaResult(MiaoshaUser user,
                                  @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return ResultVOUtil.success(ResultEnum.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return ResultVOUtil.success(result);
    }

    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO getMiaoshaPath(HttpServletRequest request, MiaoshaUser user,
                                   @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return ResultVOUtil.success(ResultEnum.SESSION_ERROR);
        }
        // 查询访问的次数
//        String uri = request.getRequestURI();
//        String key = uri + "_" + user.getId();
        return ResultVOUtil.error(ResultEnum.BIND_ERROR);

//        String path = miaoshaService.createMiaoshaPath(user, goodsId);
//        return ResultVOUtil.success(path);
    }

}

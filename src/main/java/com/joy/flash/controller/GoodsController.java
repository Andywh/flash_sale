package com.joy.flash.controller;

import com.joy.flash.VO.GoodsDetailVO;
import com.joy.flash.VO.GoodsVO;
import com.joy.flash.VO.ResultVO;
import com.joy.flash.model.MiaoshaUser;
import com.joy.flash.service.GoodsService;
import com.joy.flash.service.MiaoshaUserService;
import com.joy.flash.service.RedisService;
import com.joy.flash.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by SongLiang on 2019-09-11
 */
@Slf4j
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping(value = "/to_list", produces = "text/html")
    public String list(HttpServletRequest request, HttpServletResponse response,
                       Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        List<GoodsVO> goodsVOList = goodsService.getGoodsVOList();
        model.addAttribute("goodsList", goodsVOList);
        return "goods_list";
    }

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public ResultVO toDetail(Model model, MiaoshaUser user,
                             @PathVariable("goodsId") long goodsId) {
        GoodsVO goods = goodsService.getGoodsVOByGoodsId(goodsId);
        //
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;

        if (now < startAt) { // 秒杀还没开始，
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now) / 1000);
        } else if (now > endAt) { // 秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else { // 秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        goodsDetailVO.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVO.setRemainSeconds(remainSeconds);
        goodsDetailVO.setGoods(goods);
        return ResultVOUtil.success(goodsDetailVO);
    }

    @RequestMapping(value = "/to_detail2/{goodsId}")
    public String toDetail2(Model model, MiaoshaUser user,
    @PathVariable("goodsId") long goodsId) {
        GoodsVO goods = goodsService.getGoodsVOByGoodsId(goodsId);
        model.addAttribute("goods", goods);
        model.addAttribute("user", user);
        //
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;

        if (now < startAt) { // 秒杀还没开始，
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now) / 1000);
        } else if (now > endAt) { // 秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else { // 秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        return "goods_detail";
    }

}

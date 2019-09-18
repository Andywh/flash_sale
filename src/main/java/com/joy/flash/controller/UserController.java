package com.joy.flash.controller;

import com.joy.flash.VO.ResultVO;
import com.joy.flash.model.MiaoshaUser;
import com.joy.flash.service.RedisService;
import com.joy.flash.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by SongLiang on 2019-09-17
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    RedisService redisService;

    @RequestMapping("/info")
    @ResponseBody
    public ResultVO<MiaoshaUser> info(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        log.info("user: {}", user);
        return ResultVOUtil.success(user);
    }

}

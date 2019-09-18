package com.joy.flash.controller;

import com.joy.flash.VO.ResultVO;
import com.joy.flash.redis.UserKey;
import com.joy.flash.model.User;
import com.joy.flash.service.RedisService;
import com.joy.flash.service.MiaoshaUserService;
import com.joy.flash.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by SongLiang on 2019-09-09
 */
@Controller
public class HelloWorldController {

    @Autowired
    private MiaoshaUserService userService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

//    @GetMapping("/getUser/{id}")
//    @ResponseBody
//    public ResultVO getUser(@PathVariable("id") int id) {
//        User user = userService.getUserById(id);
//        return ResultVOUtil.success(user);
//    }
//
//    @GetMapping("/insert")
//    @ResponseBody
//    public ResultVO getUser() {
//        return ResultVOUtil.success(userService.insert());
//    }

    @GetMapping("/redis/get")
    @ResponseBody
    public ResultVO getRedis() {
        User user = redisService.get(UserKey.getById, "key3", User.class);
        return ResultVOUtil.success(user);
//        return ResultVOUtil.success(redisService.get("key1", Long.class));
    }

    @GetMapping("/redis/set")
    @ResponseBody
    public ResultVO setRedis() {
//        boolean result = redisService.set("key1", 234L);
//        return ResultVOUtil.success(result);
        User user = new User(1, "11111");
        boolean ret = redisService.set(UserKey.getById, "key3", user);
        return ResultVOUtil.success(ret);
    }

    public static void main(String[] args) {
        System.out.println(UserKey.getById.getPrefix());
    }


}

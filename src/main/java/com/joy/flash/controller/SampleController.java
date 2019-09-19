package com.joy.flash.controller;

import com.joy.flash.VO.ResultVO;
import com.joy.flash.rabbitmq.MQSender;
import com.joy.flash.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by SongLiang on 2019-09-19
 */
@Controller
public class SampleController {

    @Autowired
    MQSender sender;

    @RequestMapping("/mq/{msg}")
    @ResponseBody
    public ResultVO mq(@PathVariable("msg") String message) {
        sender.send(message);
        return ResultVOUtil.success("hello world");
    }

}

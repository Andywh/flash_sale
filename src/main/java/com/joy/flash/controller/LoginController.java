package com.joy.flash.controller;

import com.joy.flash.VO.LoginVO;
import com.joy.flash.VO.ResultVO;
import com.joy.flash.exception.GlobalException;
import com.joy.flash.service.MiaoshaUserService;
import com.joy.flash.utils.ResultVOUtil;
import com.mysql.cj.CacheAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by SongLiang on 2019-09-10
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MiaoshaUserService userService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public ResultVO doLogin(HttpServletResponse response,
                            @Valid LoginVO loginVO) {
        // 使用异常拦截器之后，下面的 try-catch 代码就不需要了
//        try {
//            token = userService.login(response, loginVO);
//        } catch (GlobalException e) {
//            return ResultVOUtil.error(e.getResultEnum());
//        }
        String token = userService.login(response, loginVO);
        return ResultVOUtil.success(token);
    }

}

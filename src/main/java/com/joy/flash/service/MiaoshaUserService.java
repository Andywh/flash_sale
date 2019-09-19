package com.joy.flash.service;

import com.joy.flash.VO.LoginVO;
import com.joy.flash.dao.MiaoshaUserMapper;
import com.joy.flash.enums.ResultEnum;
import com.joy.flash.exception.GlobalException;
import com.joy.flash.model.MiaoshaUser;
import com.joy.flash.redis.MiaoshaUserKey;
import com.joy.flash.utils.MD5Util;
import com.joy.flash.utils.UUIDUtil;
import com.joy.flash.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by SongLiang on 2019-09-09
 */
@Slf4j
@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserMapper miaoshaUserMapper;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(long id) {
        // 取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, ""+id, MiaoshaUser.class);
        if (user != null) {
            return user;
        }
        // 取数据库
        user = miaoshaUserMapper.getById(id);
        if (user != null) {
            // 存 redis
            redisService.set(MiaoshaUserKey.getById, ""+id, user);
        }
        return user;
    }

    public boolean updatePassword(String token, long id, String formPass) {
        // 取user
        MiaoshaUser user = getById(id);
        if (user == null) {
            throw new GlobalException(ResultEnum.MOBILE_NOT_EXIST);
        }
        // 更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDbPass(formPass, user.getSalt()));
        miaoshaUserMapper.update(toBeUpdate);
        // 处理缓存
        redisService.delete(MiaoshaUserKey.getById, ""+id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token, token, user);
        return true;
    }

    public String login(HttpServletResponse response, LoginVO loginVO) {
        log.info("login...");
        log.info("loginVO: {}", loginVO);
        if (loginVO == null) {
            log.info("loginVO == null");
            throw new GlobalException(ResultEnum.SESSION_ERROR);
        }
        // 参数校验
        String formPass = loginVO.getPassword();
        String mobile = loginVO.getMobile();
        log.info("formPass: {}, mobile: {}", formPass, mobile);
        if (StringUtils.isEmpty(formPass)) {
            log.info("formPass is empty");
            throw new GlobalException(ResultEnum.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(mobile)) {
            log.info("mobile is empty");
            throw new GlobalException(ResultEnum.MOBILE_EMPTY);
        }
        if (!ValidatorUtil.isMobile(mobile)) {
            throw new GlobalException(ResultEnum.MOBILE_ERROR);
        }

        // 判断手机号是否存在
        log.info("user");
        MiaoshaUser user = getById(Long.parseLong(mobile));
        log.info("user user: {}", user);
        if (user == null) {
            log.info("user is null, mobile: {}", mobile);
            throw new GlobalException(ResultEnum.MOBILE_NOT_EXIST);
        }
        // 验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDbPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(ResultEnum.PASSWORD_ERROR);
        }
        // 生成 cookie
        String token = UUIDUtil.uuid();
        log.info("token: {}", token);
        redisService.set(MiaoshaUserKey.token, token, user);
        addCookie(response, token, user);
        return token;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        return;
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        // 延长有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }
}

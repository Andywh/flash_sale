package com.joy.flash.redis;

import com.joy.flash.model.MiaoshaUser;

/**
 * Created by SongLiang on 2019-09-09
 */
public class MiaoshaUserKey extends BasePrefix {

    private static final int TOKEN_EXPIRE = 60 * 60 * 24 * 2;

    private MiaoshaUserKey(String prefix) {
        super(prefix);
    }

    private MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0, "id");

}

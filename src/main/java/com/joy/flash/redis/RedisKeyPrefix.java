package com.joy.flash.redis;

/**
 * Created by SongLiang on 2019-09-09
 */
public interface RedisKeyPrefix {

    int expireSeconds();

    String getPrefix();

}

package com.joy.flash.redis;

/**
 * Created by SongLiang on 2019-09-09
 */
public class OrderKey extends BasePrefix {

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}

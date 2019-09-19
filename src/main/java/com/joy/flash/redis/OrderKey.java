package com.joy.flash.redis;

import com.mysql.cj.x.protobuf.MysqlxCrud;

/**
 * Created by SongLiang on 2019-09-09
 */
public class OrderKey extends BasePrefix {

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");

}

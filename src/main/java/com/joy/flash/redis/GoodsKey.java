package com.joy.flash.redis;

/**
 * Created by SongLiang on 2019-09-18
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(String prefix) {
        super(prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey("id");

}

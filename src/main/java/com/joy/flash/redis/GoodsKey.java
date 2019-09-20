package com.joy.flash.redis;

/**
 * Created by SongLiang on 2019-09-18
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    private GoodsKey(String prefix) {
        super(prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "id");

    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");

    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0, "gs");

}

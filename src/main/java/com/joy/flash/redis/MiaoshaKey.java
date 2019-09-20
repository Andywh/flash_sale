package com.joy.flash.redis;

/**
 * Created by SongLiang on 2019-09-20
 */
public class MiaoshaKey extends BasePrefix {

    private MiaoshaKey(String prefix) {
        super(prefix);
    }

    private MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0,"go");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60,"mp");

}

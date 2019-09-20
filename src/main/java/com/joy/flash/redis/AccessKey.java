package com.joy.flash.redis;

/**
 * Created by SongLiang on 2019-09-20
 */

public class AccessKey extends BasePrefix {

    private AccessKey(String prefix) {
        super(prefix);
    }

    private AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey accessKey = new AccessKey(5, "access");

    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }

}

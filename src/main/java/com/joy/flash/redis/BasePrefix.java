package com.joy.flash.redis;

/**
 * Created by SongLiang on 2019-09-09
 */
public abstract class BasePrefix implements RedisKeyPrefix {

    private int expireSeconds;

    private String prefix;

    public BasePrefix() {

    }

    public BasePrefix(String prefix) { // 0 代表永不过期
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() { // 默认 0 代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }

}

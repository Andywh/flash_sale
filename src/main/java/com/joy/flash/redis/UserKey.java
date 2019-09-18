package com.joy.flash.redis;

/**
 * Created by SongLiang on 2019-09-09
 */
public class UserKey extends BasePrefix {

    public UserKey() {
    }

    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");


}

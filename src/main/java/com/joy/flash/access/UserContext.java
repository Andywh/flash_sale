package com.joy.flash.access;

import com.joy.flash.model.MiaoshaUser;

/**
 * Created by SongLiang on 2019-09-20
 */

public class UserContext {

    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();

    public static void setUser(MiaoshaUser user) {
        userHolder.set(user);
    }

    public static MiaoshaUser getUser(MiaoshaUser user) {
        return userHolder.get();
    }

}

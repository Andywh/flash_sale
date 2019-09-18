package com.joy.flash.utils;

import java.util.UUID;

/**
 * Created by SongLiang on 2019-09-10
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}

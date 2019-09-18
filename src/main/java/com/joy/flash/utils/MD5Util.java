package com.joy.flash.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by SongLiang on 2019-09-10
 */
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFromPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDbPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String input, String saltDB) {
        String formPass = inputPassToFromPass(input);
        String dbPass = formPassToDbPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456")); //
        System.out.println(formPassToDbPass(inputPassToFromPass("123456"), "1a2b3c4d")); //
        System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));
    }
}

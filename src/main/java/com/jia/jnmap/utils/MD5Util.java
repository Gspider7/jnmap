package com.jia.jnmap.utils;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 *
 * @author xutao
 * @date 2020-03-15 12:24
 */
public class MD5Util {

    public static String MD5(String data) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));

            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString().toUpperCase();
    }
}

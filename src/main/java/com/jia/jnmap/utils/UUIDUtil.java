package com.jia.jnmap.utils;

import java.util.UUID;

/**
 * @author xutao
 * @date 2020-03-17 23:17
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}

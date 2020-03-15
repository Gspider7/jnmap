package com.jia.jnmap;

import com.jia.jnmap.utils.MD5Util;
import org.junit.jupiter.api.Test;

/**
 * @author xutao
 * @date 2020-03-15 12:21
 */
class BaseTest {

    @Test
    public void testMD5() {
        byte[] bytes = new String("ayufan").getBytes();

        System.out.println(MD5Util.MD5("ayufan"));
    }

}
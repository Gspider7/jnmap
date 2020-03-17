package com.jia.jnmap.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jia.jnmap.bean.web.Response;

/**
 * @author xutao
 * @date 2020-03-17 23:10
 */
public class WebUtil {


    public static String success() {
        Response response = new Response(0, "success");

        try {
            return JacksonUtil.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("jackson序列化异常", e);
        }
    }
}

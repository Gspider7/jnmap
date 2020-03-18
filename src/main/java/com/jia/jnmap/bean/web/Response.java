package com.jia.jnmap.bean.web;

import java.io.Serializable;

/**
 * 响应结果
 *
 * @author xutao
 * @date 2020-03-17 23:11
 */
public class Response implements Serializable {

    private Integer code;

    private String message;

    public Response() {
    }

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.jia.jnmap.domain;

import java.io.Serializable;

/**
 * websocket消息基础结构
 *
 * @author xutao
 * @date 2020-04-03 07:19
 */
public class WebsocketMessageVO implements Serializable {

    public static final String TYPE_VULN_UPLOAD = "VULN_UPLOAD";
    public static final String TYPE_SCAN_RESULT = "SCAN_RESULT";

    // 消息类型
    private String type;

    public WebsocketMessageVO() {
    }

    public WebsocketMessageVO(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

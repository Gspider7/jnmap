package com.jia.jnmap.domain;

import com.jia.jnmap.entity.Scan;

/**
 * 发送给前端的扫描状态结构体
 *
 * @version 1.0.0
 * @date 2020-03-30 15:51
 */
public class ScanStatusVO extends WebsocketMessageVO {

    // 扫描id
    private String id;
    // 扫描名称
    private String name;
    // 状态
    private String status;

    public ScanStatusVO() {
    }

    public ScanStatusVO(Scan scan, String status) {
        this.id = scan == null ? null : scan.getId();
        this.name = scan == null ? null : scan.getName();
        this.status = status;

        setType(TYPE_SCAN_RESULT);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

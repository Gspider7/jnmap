package com.jia.jnmap.domain;

import java.io.Serializable;

/**
 * 发送给前端的扫描状态结构体
 *
 * @version 1.0.0
 * @date 2020-03-30 15:51
 */
public class ScanStatusVO implements Serializable {

    // 扫描id
    private String scanId;
    // 状态
    private String status;

    public ScanStatusVO() {
    }

    public ScanStatusVO(String scanId, String status) {
        this.scanId = scanId;
        this.status = status;
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.jia.jnmap.entity;

import java.util.Date;

/**
 * 系统日志
 *
 * @author xutao
 * @date 2020-04-03 06:21
 */
public class SystemLog {

    public static final String OP_VULN_OPLOAD = "VULN_UPLOAD";

    // id
    private Long id;
    // 操作
    private String operation;
    // 用户名
    private String username;
    // 操作状态，0：失败，1：成功
    private Integer status;
    // 操作时间
    private Date operationTime;

    public SystemLog() {
    }

    public SystemLog(String operation, String username, Integer status) {
        this.operation = operation;
        this.username = username;
        this.status = status;

        this.operationTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }
}

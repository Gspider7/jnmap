package com.jia.jnmap.entity;

import com.jia.jnmap.utils.UUIDUtil;

import java.util.Date;

/**
 * 自定义扫描
 *
 * @author xutao
 * @date 2020-03-29 15:48
 */
public class Scan {

    // uuid
    private String id;
    // 扫描名称
    private String name;
    // 扫描目标
    private String target;
    // 创建时间
    private Date createTime;

    // 扫描策略
    private String strategy;
    // 扫描时间
    private Date scanTime;

    public Scan() {
    }

    public Scan(String name, String target) {
        this.id = UUIDUtil.uuid();
        this.createTime = new Date();

        this.name = name;
        this.target = target;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Date getScanTime() {
        return scanTime;
    }

    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
    }
}

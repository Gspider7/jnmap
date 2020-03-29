package com.jia.jnmap.entity;

import com.jia.jnmap.utils.UUIDUtil;

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

    public Scan() {
    }

    public Scan(String name, String target) {
        this.id = UUIDUtil.uuid();

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
}

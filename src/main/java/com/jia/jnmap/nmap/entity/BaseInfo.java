package com.jia.jnmap.nmap.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaseInfo {

    private String cpe;

    // 匹配到的漏洞id列表
    private List<String> vulnerability = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseInfo baseInfo = (BaseInfo) o;
        return Objects.equals(cpe, baseInfo.cpe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpe);
    }

    public String getCpe() {
        return cpe;
    }

    public void setCpe(String cpe) {
        this.cpe = cpe;
    }

    public List<String> getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(List<String> vulnerability) {
        this.vulnerability = vulnerability;
    }

}

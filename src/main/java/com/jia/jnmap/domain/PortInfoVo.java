package com.jia.jnmap.domain;


import com.jia.jnmap.entity.VulnBaseInfo;
import com.jia.jnmap.nmap.entity.PortInfo;

import java.util.ArrayList;
import java.util.List;

public class PortInfoVo {

    private PortInfo portInfo;

    private List<VulnBaseInfo> vulnerabilities = new ArrayList<>();

    public PortInfoVo(PortInfo portInfo){
        this.portInfo = portInfo;
    }

    public PortInfo getPortInfo() {
        return portInfo;
    }

    public void setPortInfo(PortInfo portInfo) {
        this.portInfo = portInfo;
    }

    public List<VulnBaseInfo> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(List<VulnBaseInfo> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }
}

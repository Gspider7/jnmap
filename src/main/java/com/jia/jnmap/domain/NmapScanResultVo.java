package com.jia.jnmap.domain;


import com.jia.jnmap.entity.NmapScanResult;
import com.jia.jnmap.entity.VulnBaseInfo;

import java.util.List;

public class NmapScanResultVo {

    private Integer vulnerabilityHighCount = 0;

    private Integer vulnerabilityMediumCount = 0;

    private Integer vulnerabilityLowCount = 0;

    private Integer vulnerabilityCriticalCount = 0;

    private Integer vulnerabilityUnknowCount = 0;

    private List<VulnBaseInfo> vulnerabilities;

    private NmapScanResult nmapScanResult;

    public NmapScanResultVo(NmapScanResult nmapScanResult) {
        this.nmapScanResult = nmapScanResult;
    }

    public Integer getVulnerabilityHighCount() {
        return vulnerabilityHighCount;
    }

    public void setVulnerabilityHighCount(Integer vulnerabilityHighCount) {
        this.vulnerabilityHighCount = vulnerabilityHighCount;
    }

    public Integer getVulnerabilityMediumCount() {
        return vulnerabilityMediumCount;
    }

    public void setVulnerabilityMediumCount(Integer vulnerabilityMediumCount) {
        this.vulnerabilityMediumCount = vulnerabilityMediumCount;
    }

    public Integer getVulnerabilityLowCount() {
        return vulnerabilityLowCount;
    }

    public void setVulnerabilityLowCount(Integer vulnerabilityLowCount) {
        this.vulnerabilityLowCount = vulnerabilityLowCount;
    }

    public Integer getVulnerabilityCriticalCount() {
        return vulnerabilityCriticalCount;
    }

    public void setVulnerabilityCriticalCount(Integer vulnerabilityCriticalCount) {
        this.vulnerabilityCriticalCount = vulnerabilityCriticalCount;
    }

    public Integer getVulnerabilityUnknowCount() {
        return vulnerabilityUnknowCount;
    }

    public void setVulnerabilityUnknowCount(Integer vulnerabilityUnknowCount) {
        this.vulnerabilityUnknowCount = vulnerabilityUnknowCount;
    }

    public List<VulnBaseInfo> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(List<VulnBaseInfo> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public NmapScanResult getNmapScanResult() {
        return nmapScanResult;
    }

    public void setNmapScanResult(NmapScanResult nmapScanResult) {
        this.nmapScanResult = nmapScanResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NmapScanResultVo that = (NmapScanResultVo) o;
        return nmapScanResult.equals(that.nmapScanResult);
    }
}

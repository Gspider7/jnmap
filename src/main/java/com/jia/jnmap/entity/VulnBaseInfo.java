package com.jia.jnmap.entity;

import java.util.Objects;

public class VulnBaseInfo {

    private String id;

    private String cveId;

    private String name;

    private String published;

    private String modified;

    private String source;

    private String severity;

    private String vulnerabilityType;

    private String thrtype;

    private String vulnerabilityDesc;

    private String vulnerabilitySolution;

    private Integer cvssBaseScore;

    private Integer cvssExploitabilityScore;

    private Integer cvssImpactScore;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCveId() {
        return cveId;
    }

    public void setCveId(String cveId) {
        this.cveId = cveId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getVulnerabilityType() {
        return vulnerabilityType;
    }

    public void setVulnerabilityType(String vulnerabilityType) {
        this.vulnerabilityType = vulnerabilityType;
    }

    public String getThrtype() {
        return thrtype;
    }

    public void setThrtype(String thrtype) {
        this.thrtype = thrtype;
    }

    public String getVulnerabilityDesc() {
        return vulnerabilityDesc;
    }

    public void setVulnerabilityDesc(String vulnerabilityDesc) {
        this.vulnerabilityDesc = vulnerabilityDesc;
    }

    public String getVulnerabilitySolution() {
        return vulnerabilitySolution;
    }

    public void setVulnerabilitySolution(String vulnerabilitySolution) {
        this.vulnerabilitySolution = vulnerabilitySolution;
    }

    public Integer getCvssBaseScore() {
        return cvssBaseScore;
    }

    public void setCvssBaseScore(Integer cvssBaseScore) {
        this.cvssBaseScore = cvssBaseScore;
    }

    public Integer getCvssExploitabilityScore() {
        return cvssExploitabilityScore;
    }

    public void setCvssExploitabilityScore(Integer cvssExploitabilityScore) {
        this.cvssExploitabilityScore = cvssExploitabilityScore;
    }

    public Integer getCvssImpactScore() {
        return cvssImpactScore;
    }

    public void setCvssImpactScore(Integer cvssImpactScore) {
        this.cvssImpactScore = cvssImpactScore;
    }

    public int compare(VulnBaseInfo vulnBaseInfo) {
        if (Objects.equals(this.cvssBaseScore, vulnBaseInfo.getCvssBaseScore())) {
            return 0;
        }
        Integer v2s = vulnBaseInfo.getCvssBaseScore();
        if (this.cvssBaseScore == null) {
            return 1;
        }
        if (v2s == null) {
            return -1;
        }
        return this.cvssBaseScore > v2s ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VulnBaseInfo that = (VulnBaseInfo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vulnerability{" +
                "id='" + id + '\'' +
                ", cveId='" + cveId + '\'' +
                ", name='" + name + '\'' +
                ", published='" + published + '\'' +
                ", modified='" + modified + '\'' +
                ", source='" + source + '\'' +
                ", severity='" + severity + '\'' +
                ", vulnerabilityType='" + vulnerabilityType + '\'' +
                ", thrtype='" + thrtype + '\'' +
                ", vulnerabilityDesc='" + vulnerabilityDesc + '\'' +
                ", vulnerabilitySolution='" + vulnerabilitySolution + '\'' +
                ", cvssBaseScore=" + cvssBaseScore +
                ", cvssExploitabilityScore=" + cvssExploitabilityScore +
                ", cvssImpactScore=" + cvssImpactScore +
                '}';
    }
}

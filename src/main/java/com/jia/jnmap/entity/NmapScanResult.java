package com.jia.jnmap.entity;

import com.jia.jnmap.nmap.entity.PortInfo;
import com.jia.jnmap.nmap.entity.ScriptInfo;
import com.jia.jnmap.nmap.entity.SystemInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class NmapScanResult {

    // UUID
    private String id;
    // 扫描id
    private String scanId;

    private String scanPlanId;

    private String templateId;
    // 扫描时间
    private String scanTime;
    // ipv4地址
    private String ipv4Address;

    private String ipv6Address;
    // mac地址
    private String macAddress;
    // 主机名
    private String hostname;
    // 主机端口信息
    private ArrayList<PortInfo> portInfos = new ArrayList<>();
    // 主机操作系统信息
    private SystemInfo systemInfo;

    private ArrayList<ScriptInfo> scriptInfos = new ArrayList<>();

    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public String getScanPlanId() {
        return scanPlanId;
    }

    public void setScanPlanId(String scanPlanId) {
        this.scanPlanId = scanPlanId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getScanTime() {
        return scanTime;
    }

    public void setScanTime(String scanTime) {
        this.scanTime = scanTime;
    }

    public String getIpv4Address() {
        return ipv4Address;
    }

    public void setIpv4Address(String ipv4Address) {
        this.ipv4Address = ipv4Address;
    }

    public String getIpv6Address() {
        return ipv6Address;
    }

    public void setIpv6Address(String ipv6Address) {
        this.ipv6Address = ipv6Address;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public ArrayList<PortInfo> getPortInfos() {
        return portInfos;
    }

    public void setPortInfos(ArrayList<PortInfo> portInfos) {
        this.portInfos = portInfos;
    }

    public SystemInfo getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

    public ArrayList<ScriptInfo> getScriptInfos() {
        return scriptInfos;
    }

    public void setScriptInfos(ArrayList<ScriptInfo> scriptInfos) {
        this.scriptInfos = scriptInfos;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void addScriptInfo(String id, String output) {
        ScriptInfo scriptInfo = new ScriptInfo();
        scriptInfo.setId(id);
        scriptInfo.setOutput(output);
        this.scriptInfos.add(scriptInfo);
    }

    public void addPortInfo(PortInfo portInfo) {
        portInfos.add(portInfo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NmapScanResult that = (NmapScanResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

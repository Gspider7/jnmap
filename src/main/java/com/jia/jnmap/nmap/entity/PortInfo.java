package com.jia.jnmap.nmap.entity;

import java.util.ArrayList;
import java.util.List;

public class PortInfo extends BaseInfo {

    // 端口号
    private Integer port;
    // 协议
    private String protocol;
    // 服务名称
    private String name;
    // 产品名称
    private String product;
    // 服务版本号
    private String version;

    private String banner;

    private List<String> gaffsmbs;

    private List<ScriptInfo> scriptInfos;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public List<String> getGaffsmbs() {
        return gaffsmbs;
    }

    public void setGaffsmbs(List<String> gaffsmbs) {
        this.gaffsmbs = gaffsmbs;
    }

    public void addGaffsmb(String id) {
        if (this.gaffsmbs == null) {
            this.gaffsmbs = new ArrayList<>();
        }
        this.gaffsmbs.add(id);
    }

    public List<ScriptInfo> getScriptInfos() {
        return scriptInfos;
    }

    public void setScriptInfos(List<ScriptInfo> scriptInfos) {
        this.scriptInfos = scriptInfos;
    }

    public void addScriptInfo(String id, String output) {
        if (this.scriptInfos == null) {
            this.scriptInfos = new ArrayList<>();
        }
        ScriptInfo scriptInfo = new ScriptInfo();
        scriptInfo.setId(id);
        scriptInfo.setOutput(output);
        this.scriptInfos.add(scriptInfo);
    }
}

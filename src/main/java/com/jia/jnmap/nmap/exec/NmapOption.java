package com.jia.jnmap.nmap.exec;

/**
 * nmap命令参数
 *
 * @author xutao@gaffassen.com
 * @version 1.0.0
 * @date 2019-12-23 16:36
 */
public enum NmapOption {

    SCAN_LIST(new String[]{"-sL"}, "主机发现 —— 列表扫描，打印网段内的主机地址列表和域名（如果有），像其它一些高级功能如端口扫描，操作系统探测或者Ping扫描的选项就没有了"),
    PING_SCAN(new String[]{"-sP"}, "主机发现 —— ping扫描"),

    SERVICE_VERSION(new String[]{"-sV"}, "服务/版本探测"),

    OS_DETECTION(new String[]{"-O"}, "操作系统发现"),

    OUTPUT_XML(new String[]{"-oX", "-"}, "指定输出格式为xml"),
    ;

    private String[] value;

    private String desc;

    NmapOption(String[] value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public boolean equal(Object o) {
        return this.value.equals(o);
    }

    public String[] getValue() {
        return value;
    }
}

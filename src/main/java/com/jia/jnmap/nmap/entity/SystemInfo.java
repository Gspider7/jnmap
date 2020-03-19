package com.jia.jnmap.nmap.entity;


public class SystemInfo extends BaseInfo {

    public static final SystemInfo UNKNOW_SYSTEM = new SystemInfo();

    static {
        UNKNOW_SYSTEM.name = "UNKNOW";
        UNKNOW_SYSTEM.vendor = "UNKNOW";
        UNKNOW_SYSTEM.family = "UNKNOW";
        UNKNOW_SYSTEM.accuracy = 100;
    }

    private String name;

    private String vendor;

    private String family;

    private Integer accuracy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return super.toString() + "SystemInfo{" +
                "name='" + name + '\'' +
                ", vendor='" + vendor + '\'' +
                ", family='" + family + '\'' +
                ", accuracy=" + accuracy +
                '}';
    }
}

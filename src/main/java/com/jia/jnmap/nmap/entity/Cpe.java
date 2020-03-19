package com.jia.jnmap.nmap.entity;


public class Cpe {

    private String cpe;

    private String startIncluding;

    private String startExcluding;

    private String endIncluding;

    private String endExcluding;

    public Cpe() {
    }

    public Cpe(String cpe) {
        this.cpe = cpe;
    }

    public String getCpe() {
        return cpe;
    }

    public void setCpe(String cpe) {
        this.cpe = cpe;
    }

    public String getStartIncluding() {
        return startIncluding;
    }

    public void setStartIncluding(String startIncluding) {
        this.startIncluding = startIncluding;
    }

    public String getStartExcluding() {
        return startExcluding;
    }

    public void setStartExcluding(String startExcluding) {
        this.startExcluding = startExcluding;
    }

    public String getEndIncluding() {
        return endIncluding;
    }

    public void setEndIncluding(String endIncluding) {
        this.endIncluding = endIncluding;
    }

    public String getEndExcluding() {
        return endExcluding;
    }

    public void setEndExcluding(String endExcluding) {
        this.endExcluding = endExcluding;
    }

    @Override
    public String toString() {
        return "Cpe{" +
                "cpe='" + cpe + '\'' +
                ", startIncluding='" + startIncluding + '\'' +
                ", startExcluding='" + startExcluding + '\'' +
                ", endIncluding='" + endIncluding + '\'' +
                ", endExcluding='" + endExcluding + '\'' +
                '}';
    }
}

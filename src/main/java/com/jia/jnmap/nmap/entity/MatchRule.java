package com.jia.jnmap.nmap.entity;

import java.util.ArrayList;
import java.util.List;

public class MatchRule {

    private String operator;

    private List<Cpe> cpeList = new ArrayList<>();

    private List<MatchRule> children = new ArrayList<>();

    public MatchRule() {
    }

    public MatchRule(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<Cpe> getCpeList() {
        return cpeList;
    }

    public void setCpeList(List<Cpe> cpeList) {
        this.cpeList = cpeList;
    }

    public List<MatchRule> getChildren() {
        return children;
    }

    public void setChildren(List<MatchRule> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MatchRule{" +
                "operator='" + operator + '\'' +
                ", cpeList=" + cpeList +
                ", children=" + children +
                '}';
    }
}

package com.jia.jnmap.entity;

import com.jia.jnmap.nmap.entity.MatchRule;

import java.util.ArrayList;

public class VulnMatchRule {

    // 漏洞id
    private String id;
    // cnnvd匹配规则
    private ArrayList<MatchRule> cnnvdMatchRules;
    // cve匹配规则
    private ArrayList<MatchRule> cveMatchRules;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<MatchRule> getCnnvdMatchRules() {
        return cnnvdMatchRules;
    }

    public void setCnnvdMatchRules(ArrayList<MatchRule> cnnvdMatchRules) {
        this.cnnvdMatchRules = cnnvdMatchRules;
    }

    public ArrayList<MatchRule> getCveMatchRules() {
        return cveMatchRules;
    }

    public void setCveMatchRules(ArrayList<MatchRule> cveMatchRules) {
        this.cveMatchRules = cveMatchRules;
    }
}

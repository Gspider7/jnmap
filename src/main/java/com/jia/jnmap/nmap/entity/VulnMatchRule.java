package com.jia.jnmap.nmap.entity;

import java.util.ArrayList;

public class VulnMatchRule {

    private String id;

    private ArrayList<MatchRule> cnnvdMatchRules;

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

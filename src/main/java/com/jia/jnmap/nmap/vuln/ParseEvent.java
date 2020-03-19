package com.jia.jnmap.nmap.vuln;


import com.jia.jnmap.nmap.entity.Vulnerability;

public interface ParseEvent {

    void onOnceEvent(Vulnerability vulnerability);

}

package com.jia.jnmap.nmap.vuln;


import com.jia.jnmap.entity.Vulnerability;

public interface ParseEvent {

    void onOnceEvent(Vulnerability vulnerability);

}

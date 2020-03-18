/*****************************************************************************
 * PROJECT: SCOUT PROJECT.
 * SUPPLIER: SCOUT TEAM.
 ***************************************************************************** 
 * FILE: NmapScanCategory.java
 * (C) Copyright Scout Team 2018, All Rights Reserved.
 *****************************************************************************/
package com.jia.jnmap.nmap.exec;

import java.util.LinkedList;
import java.util.List;

/**
 * Nmap Script Category.
 * 
 * @author liuzheng@gcsoftware.com
 * @version 1.0.0
 */
public enum NmapScanCategory {

    // ------------------------------------------------------------------------

    // AUTH      | These scripts deal with authentication credentials (or bypassing them) on the target system. Examples include 
    //           | x11-access, ftp-anon, and oracle-enum-users. Scripts which use brute force attacks to determine credentials 
    //           | are placed in the brute category instead.

    AUTH("auth"), //

    // BROADCAST | Scripts in this category typically do discovery of hosts not listed on the command line by broadcasting on the 
    //           | local network. Use the newtargets script argument to allow these scripts to automatically add the hosts they 
    //           | discover to the Nmap scanning queue.

    BROADCAST("broadcast"), //

    // BRUTE     | These scripts use brute force attacks to guess authentication credentials of a remote server. Nmap contains scripts 
    //           | for brute forcing dozens of protocols, including http-brute, oracle-brute, snmp-brute, etc.

//    BRUTE("brute", "brute.credfile", Environment.getProjectFile("conf/brute.lst").getAbsolutePath()),

    // DEFAULT   | These scripts are the default set and are run when using the -sC or -A options rather than listing scripts with 
    //           | --script. This category can also be specified explicitly like any other using --script=default. 

    DEFAULT("default"), //

    // DISCOVERY | These scripts try to actively discover more about the network by querying public registries, SNMP-enabled devices, 
    //           | directory services, and the like. Examples include html-title (obtains the title of the root path of web sites), 
    //           | smb-enum-shares (enumerates Windows shares), and snmp-sysdescr (extracts system details via SNMP).

    DISCOVERY("discovery"), //

    // DOS       | Scripts in this category may cause a denial of service. Sometimes this is done to test vulnerability to a denial 
    //           | of service method, but more commonly it is an undesired by necessary side effect of testing for a traditional 
    //           | vulnerability. These tests sometimes crash vulnerable services.

    DOS("dos"),

    // EXPLOIT   | These scripts aim to actively exploit some vulnerability. Examples include jdwp-exec and http-shellshock.

    EXPLOIT("exploit"),

    // EXTERNAL  | Scripts in this category may send data to a third-party database or other network resource. An example of this 
    //           | is whois-ip, which makes a connection to whois servers to learn about the address of the target. There is always 
    //           | the possibility that operators of the third-party database will record anything you send to them, which in many 
    //           | cases will include your IP address and the address of the target. Most scripts involve traffic strictly between 
    //           | the scanning computer and the client; any that do not are placed in this category.

    EXTERNAL("external"),

    // FUZZER    | This category contains scripts which are designed to send server software unexpected or randomized fields in 
    //           | each packet. While this technique can useful for finding undiscovered bugs and vulnerabilities in software, 
    //           | it is both a slow process and bandwidth intensive. An example of a script in this category is dns-fuzz, which 
    //           | bombards a DNS server with slightly flawed domain requests until either the server crashes or a user specified 
    //           | time limit elapses.

    FUZZER("fuzzer"), //

    // INTRUSIVE | These are scripts that cannot be classified in the safe category because the risks are too high that they will 
    //           | crash the target system, use up significant resources on the target host (such as bandwidth or CPU time), or 
    //           | otherwise be perceived as malicious by the target's system administrators. Examples are http-open-proxy (which 
    //           | attempts to use the target server as an HTTP proxy) and snmp-brute (which tries to guess a device's SNMP community 
    //           | string by sending common values such as public, private, and cisco). Unless a script is in the special version 
    //           | category, it should be categorized as either safe or intrusive.

    INTRUSIVE("intrusive"), //

    // MALWARE   | These scripts test whether the target platform is infected by malware or backdoors. Examples include smtp-strangeport, 
    //           | which watches for SMTP servers running on unusual port numbers, and auth-spoof, which detects identd spoofing 
    //           | daemons which provide a fake answer before even receiving a query. Both of these behaviors are commonly associated 
    //           | with malware infections.

    MALWARE("malware"), //

    // SAFE      | Scripts which weren't designed to crash services, use large amounts of network bandwidth or other resources, 
    //           | or exploit security holes are categorized as safe. These are less likely to offend remote administrators, though 
    //           | (as with all other Nmap features) we cannot guarantee that they won't ever cause adverse reactions. Most of 
    //           | these perform general network discovery. Examples are ssh-hostkey (retrieves an SSH host key) and html-title 
    //           | (grabs the title from a web page). Scripts in the version category are not categorized by safety, but any other 
    //           | scripts which aren't in safe should be placed in intrusive.

    SAFE("safe"), //

    // VERSION   | The scripts in this special category are an extension to the version detection feature and cannot be selected 
    //           | explicitly. They are selected to run only if version detection (-sV) was requested. Their output cannot be distinguished 
    //           | from version detection output and they do not produce service or host script results. Examples are skypev2-version, 
    //           | pptp-version, and iax2-version.

    VERSION("version"), //

    // VULN      | These scripts check for specific known vulnerabilities and generally only report results if they are found. 
    //           | Examples include realvnc-auth-bypass and afp-path-vuln.

    VULN("vuln"), //

    // ------------------------------------------------------------------------

    GAFF_VULN("vuln", "timeout", "30s"), // 深度安全扫描

    GAFF_MALWARE("malware", "timeout", "30s"), // 软件后门检测

    GAFF_SHARE("gaffshare", "timeout", "30s"), // 共享安全扫描

    GAFF_SMTP("gaffsmtp", "timeout", "30s"), // SMTP 服务安全扫描

    GAFF_FTP("gaffftp", "timeout", "30s"), // FTP 服务安全扫描

    GAFF_DNS("gaffdns", "timeout", "30s"), // DNS 服务安全扫描

    GAFF_SNMP("gaffsnmp", "timeout", "30s"), // SNMP 服务安全扫描

    GAFF_RPC("gaffrpc", "timeout", "30s"), // RPC 服务安全扫描

    GAFF_NFS("gaffnfs", "timeout", "30s"), // NFS 服务安全扫描

    GAFF_DATABASE("gaffdb", "timeout", "30s"), // 数据库安全扫描

    GAFF_WEB("gaffweb", "timeout", "30s"), // WEB 服务安全扫描

    GAFF_SMB("gaffsmb", "timeout", "30s"), // 勒索病毒扫描

//    GAFF_BRUTE("brute", "timeout", "30s", "userdb", Environment.getProjectFile("conf/user.lst").getAbsolutePath(), "passdb", Environment.getProjectFile("conf/pwd.lst").getAbsolutePath()), // 弱密码安全扫描

    ;

    // ------------------------------------------------------------------------

    private final String category;

    private final List<String> arguments = new LinkedList<>();

    // ------------------------------------------------------------------------

    private NmapScanCategory(String category) {
        this.category = category;
    }

    private NmapScanCategory(String category, String n1, String v1) {
        this.category = category;
        arguments.add(String.format("%s=%s", n1, v1));
    }

    private NmapScanCategory(String category, String n1, String v1, String n2, String v2) {
        this.category = category;
        arguments.add(String.format("%s=%s", n1, v1));
        arguments.add(String.format("%s=%s", n2, v2));
    }

    private NmapScanCategory(String category, String n1, String v1, String n2, String v2, String n3, String v3) {
        this.category = category;
        arguments.add(String.format("%s=%s", n1, v1));
        arguments.add(String.format("%s=%s", n2, v2));
        arguments.add(String.format("%s=%s", n3, v3));
    }

    // ------------------------------------------------------------------------

    public String script() {
        return category;
    }

    public List<String> scriptArguments() {
        return arguments;
    }

}

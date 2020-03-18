/*****************************************************************************
 * PROJECT: SCOUT PROJECT.
 * SUPPLIER: SCOUT TEAM.
 ***************************************************************************** 
 * FILE: NmapScanTechnique.java
 * (C) Copyright Scout Team 2018, All Rights Reserved.
 *****************************************************************************/
package com.jia.jnmap.nmap.exec;

/**
 * 
 * 
 * @author liuzheng@gcsoftware.com
 * @version 1.0.0
 */
public enum NmapScanTechnique {

    // ------------------------------------------------------------------------

    // -sS (TCP SYN scan)

    // SYN scan is the default and most popular scan option for good reasons. It can be performed quickly, scanning thousands of 
    // ports per second on a fast network not hampered by restrictive firewalls. It is also relatively unobtrusive and stealthy 
    // since it never completes TCP connections. SYN scan works against any compliant TCP stack rather than depending on idiosyncrasies 
    // of specific platforms as Nmap's FIN/NULL/Xmas, Maimon and idle scans do. It also allows clear, reliable differentiation 
    // between the open, closed, and filtered states.

    // This technique is often referred to as half-open scanning, because you don't open a full TCP connection. You send a SYN 
    // packet, as if you are going to open a real connection and then wait for a response. A SYN/ACK indicates the port is listening (open), 
    // while a RST (reset) is indicative of a non-listener. If no response is received after several retransmissions, the port 
    // is marked as filtered. The port is also marked filtered if an ICMP unreachable error (type 3, code 0, 1, 2, 3, 9, 10, or 13) 
    // is received. The port is also considered open if a SYN packet (without the ACK flag) is received in response. This can be 
    // due to an extremely rare TCP feature known as a simultaneous open or split handshake connection.

    TCP_SYN("-sS"), //

    // -sT (TCP connect scan)

    // TCP connect scan is the default TCP scan type when SYN scan is not an option. This is the case when a user does not have 
    // raw packet privileges. Instead of writing raw packets as most other scan types do, Nmap asks the underlying operating system 
    // to establish a connection with the target machine and port by issuing the connect system call. This is the same high-level 
    // system call that web browsers, P2P clients, and most other network-enabled applications use to establish a connection. It 
    // is part of a programming interface known as the Berkeley Sockets API. Rather than read raw packet responses off the wire, 
    // Nmap uses this API to obtain status information on each connection attempt.

    // When SYN scan is available, it is usually a better choice. Nmap has less control over the high level connect call than with 
    // raw packets, making it less efficient. The system call completes connections to open target ports rather than performing 
    // the half-open reset that SYN scan does. Not only does this take longer and require more packets to obtain the same information, 
    // but target machines are more likely to log the connection. A decent IDS will catch either, but most machines have no such 
    // alarm system. Many services on your average Unix system will add a note to syslog, and sometimes a cryptic error message, 
    // when Nmap connects and then closes the connection without sending data. Truly pathetic services crash when this happens, 
    // though that is uncommon. An administrator who sees a bunch of connection attempts in her logs from a single system should 
    // know that she has been connect scanned.
    TCP_CONNECT("-sT"), //

    // -sU (UDP scans)

    // While most popular services on the Internet run over the TCP protocol, UDP services are widely deployed. DNS, SNMP, and 
    // DHCP (registered ports 53, 161/162, and 67/68) are three of the most common. Because UDP scanning is generally slower and 
    // more difficult than TCP, some security auditors ignore these ports. This is a mistake, as exploitable UDP services are quite 
    // common and attackers certainly don't ignore the whole protocol. Fortunately, Nmap can help inventory UDP ports.

    // UDP scan is activated with the -sU option. It can be combined with a TCP scan type such as SYN scan (-sS) to check both protocols 
    // during the same run.

    // UDP scan works by sending a UDP packet to every targeted port. For some common ports such as 53 and 161, a protocol-specific 
    // payload is sent to increase response rate, but for most ports the packet is empty unless the --data, --data-string, or --data-length 
    // options are specified. If an ICMP port unreachable error (type 3, code 3) is returned, the port is closed. Other ICMP unreachable 
    // errors (type 3, codes 0, 1, 2, 9, 10, or 13) mark the port as filtered. Occasionally, a service will respond with a UDP 
    // packet, proving that it is open. If no response is received after retransmissions, the port is classified as open|filtered. 
    // This means that the port could be open, or perhaps packet filters are blocking the communication. Version detection (-sV) 
    // can be used to help differentiate the truly open ports from the filtered ones.

    // A big challenge with UDP scanning is doing it quickly. Open and filtered ports rarely send any response, leaving Nmap to 
    // time out and then conduct retransmissions just in case the probe or response were lost. Closed ports are often an even bigger 
    // problem. They usually send back an ICMP port unreachable error. But unlike the RST packets sent by closed TCP ports in response 
    // to a SYN or connect scan, many hosts rate limit ICMP port unreachable messages by default. Linux and Solaris are particularly 
    // strict about this. For example, the Linux 2.4.20 kernel limits destination unreachable messages to one per second 
    // (in net/ipv4/icmp.c).

    // Nmap detects rate limiting and slows down accordingly to avoid flooding the network with useless packets that the target 
    // machine will drop. Unfortunately, a Linux-style limit of one packet per second makes a 65,536-port scan take more than 18 
    // hours. Ideas for speeding your UDP scans up include scanning more hosts in parallel, doing a quick scan of just the popular 
    // ports first, scanning from behind the firewall, and using --host-timeout to skip slow hosts.
    UDP("-sU"),

    // -sSU (TCP SYN scan and UDP scans)

    MIX("-sSU");

    // ------------------------------------------------------------------------

    private final String option;

    // ------------------------------------------------------------------------

    private NmapScanTechnique(String option) {
        this.option = option;
    }

    // ------------------------------------------------------------------------

    public String option() {
        return option;
    }

}

/*****************************************************************************
 * PROJECT: SCOUT PROJECT.
 * SUPPLIER: SCOUT TEAM.
 *****************************************************************************
 * FILE: NmapCommandBuilder.java
 * (C) Copyright Scout Team 2018, All Rights Reserved.
 *****************************************************************************/
package com.jia.jnmap.exec;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzheng@gcsoftware.com
 * @version 1.0.0
 */
public class NmapCommandBuilder {

    // ------------------------------------------------------------------------

    // nmap安装路径
    public final static File NMAP_EXEC_FILE = FileUtils.getFile("/usr/bin/nmap");

    // ------------------------------------------------------------------------

    // default portmark.

    public final static String OPTION_FAST_PORTMARK = "22,23,25,80,111,135,139,443,445,554,1099,1521,3306,3389,5985,5986,8080,8081";

    public final static String OPTION_VERBOSE_PORTMARK = // 
            /*  */"1,3-4,6-7,9,13,17,19-26,30,32-33,37,42-43,49,53,70,79-85,88-90,99-100,106,109-111,113,119,125,135,139,143-144,146,161,163," //
            + "179,199,211-212,222,254-256,259,264,280,301,306,311,340,366,389,406-407,416-417,425,427,443-445,458,464-465,481,497,500," //
            + "512-515,524,541,543-545,548,554-555,563,587,593,616-617,625,631,636,646,648,666-668,683,687,691,700,705,711,714,720,722," //
            + "726,749,765,777,783,787,800-801,808,843,873,880,888,898,900-903,911-912,981,987,990,992-993,995,999-1002,1007,1009-1011," //
            + "1021-1100,1102,1104-1108,1110-1114,1117,1119,1121-1124,1126,1130-1132,1137-1138,1141,1145,1147-1149,1151-1152,1154,1163-1166," //
            + "1169,1174-1175,1183,1185-1187,1192,1198-1199,1201,1213,1216-1218,1233-1234,1236,1244,1247-1248,1259,1271-1272,1277,1287,1296," //
            + "1300-1301,1309-1311,1322,1328,1334,1352,1417,1433-1434,1443,1455,1461,1494,1500-1501,1503,1521,1524,1533,1556,1580,1583,1594," //
            + "1600,1641,1658,1666,1687-1688,1700,1717-1721,1723,1755,1761,1782-1783,1801,1805,1812,1839-1840,1862-1864,1875,1900,1914,1935," //
            + "1947,1971-1972,1974,1984,1998-2010,2013,2020-2022,2030,2033-2035,2038,2040-2043,2045-2049,2065,2068,2099-2100,2103,2105-2107," //
            + "2111,2119,2121,2126,2135,2144,2160-2161,2170,2179,2190-2191,2196,2200,2222,2251,2260,2288,2301,2323,2366,2381-2383,2393-2394," //
            + "2399,2401,2492,2500,2522,2525,2557,2601-2602,2604-2605,2607-2608,2638,2701-2702,2710,2717-2718,2725,2800,2809,2811,2869,2875," //
            + "2909-2910,2920,2967-2968,2998,3000-3001,3003,3005-3007,3011,3013,3017,3030-3031,3052,3071,3077,3128,3168,3211,3221,3260-3261," //
            + "3268-3269,3283,3300-3301,3306,3322-3325,3333,3351,3367,3369-3372,3389-3390,3404,3476,3493,3517,3527,3546,3551,3580,3659,3689-3690," //
            + "3703,3737,3766,3784,3800-3801,3809,3814,3826-3828,3851,3869,3871,3878,3880,3889,3905,3914,3918,3920,3945,3971,3986,3995,3998," //
            + "4000-4006,4045,4111,4125-4126,4129,4224,4242,4279,4321,4343,4443-4446,4449,4550,4567,4662,4848,4899-4900,4998,5000-5004,5009," //
            + "5030,5033,5050-5051,5054,5060-5061,5080,5087,5100-5102,5120,5190,5200,5214,5221-5222,5225-5226,5269,5280,5298,5357,5405,5414," //
            + "5431-5432,5440,5500,5510,5544,5550,5555,5560,5566,5631,5633,5666,5678-5679,5718,5730,5800-5802,5810-5811,5815,5822,5825,5850," //
            + "5859,5862,5877,5900-5904,5906-5907,5910-5911,5915,5922,5925,5950,5952,5959-5963,5985-5989,5998-6007,6009,6025,6059,6100-6101," //
            + "6106,6112,6123,6129,6156,6346,6389,6502,6510,6543,6547,6565-6567,6580,6646,6666-6669,6689,6692,6699,6779,6788-6789,6792,6839," //
            + "6881,6901,6969,7000-7002,7004,7007,7019,7025,7070,7100,7103,7106,7200-7201,7402,7435,7443,7496,7512,7625,7627,7676,7741,7777-7778," //
            + "7800,7911,7920-7921,7937-7938,7999-8002,8007-8011,8021-8022,8031,8042,8045,8080-8090,8093,8099-8100,8180-8181,8192-8194,8200,8222," //
            + "8254,8290-8292,8300,8333,8383,8400,8402,8443,8500,8600,8649,8651-8652,8654,8701,8800,8873,8888,8899,8994,9000-9003,9009-9011," //
            + "9040,9050,9071,9080-9081,9090-9091,9099-9103,9110-9111,9200,9207,9220,9290,9415,9418,9485,9500,9502-9503,9535,9575,9593-9595," //
            + "9618,9666,9876-9878,9898,9900,9917,9929,9943-9944,9968,9998-10004,10009-10010,10012,10024-10025,10082,10180,10215,10243,10566," //
            + "10616-10617,10621,10626,10628-10629,10778,11110-11111,11967,12000,12174,12265,12345,13456,13722,13782-13783,14000,14238,14441-14442," //
            + "15000,15002-15004,15660,15742,16000-16001,16012,16016,16018,16080,16113,16992-16993,17877,17988,18040,18101,18988,19101,19283,19315," //
            + "19350,19780,19801,19842,20000,20005,20031,20221-20222,20828,21571,22939,23502,24444,24800,25734-25735,26214,27000,27352-27353," //
            + "27355-27356,27715,28201,30000,30718,30951,31038,31337,32768-32785,33354,33899,34571-34573,35500,38292,40193,40911,41511,42510," //
            + "44176,44442-44443,44501,45100,48080,49152-49161,49163,49165,49167,49175-49176,49400,49999-50003,50006,50300,50389,50500,50636," //
            + "50800,51103,51493,52673,52822,52848,52869,54045,54328,55055-55056,55555,55600,56737-56738,57294,57797,58080,60020,60443,61532," //
            + "61900,62078,63331,64623,64680,65000,65129,65389";

    // -oX <filespec> (XML output)

    // Requests that XML output be directed to the given filename. Nmap includes a document type definition (DTD) which allows 
    // XML parsers to validate Nmap XML output. While it is primarily intended for programmatic use, it can also help humans interpret 
    // Nmap XML output. The DTD defines the legal elements of the format, and often enumerates the attributes and values they 
    // can take on. The latest version is always available from https://svn.nmap.org/nmap/docs/nmap.dtd.
    // XML offers a stable format that is easily parsed by software. Free XML parsers are available for all major computer languages, 
    // including C/C++, Perl, Python, and Java. People have even written bindings for most of these languages to handle Nmap output 
    // and execution specifically. Examples are Nmap::Scanner and Nmap::Parser in Perl CPAN. In almost all cases that a non-trivial 
    // application interfaces with Nmap, XML is the preferred format.
    // The XML output references an XSL stylesheet which can be used to format the results as HTML. The easiest way to use this 
    // is simply to load the XML output in a web browser such as Firefox or IE. By default, this will only work on the machine 
    // you ran Nmap on (or a similarly configured one) due to the hard-coded nmap.xsl filesystem path. Use the --webxml or --stylesheet 
    // options to create portable XML files that render as HTML on any web-connected machine.

    public final static String[] OPTION_OUTPUT_XML = new String[]{"-oX", "-"};

    // --open (Show only open (or possibly open) ports)

    // Sometimes you only care about ports you can actually connect to (open ones), and don't want results cluttered with closed, 
    // filtered, and closed|filtered ports. Output customization is normally done after the scan using tools such as grep, awk, 
    // and Perl, but this feature was added due to overwhelming requests. Specify --open to only see hosts with at least one open, 
    // open|filtered, or unfiltered port, and only see ports in those states. These three states are treated just as they normally 
    // are, which means that open|filtered and unfiltered may be condensed into counts if there are an overwhelming number of them.

    public final static String OPTION_OPEN = "--open";

    // -T5

    // T5 does the equivalent of --max-rtt-timeout 300ms --min-rtt-timeout 50ms --initial-rtt-timeout 250ms --max-retries 2 --host-timeout 
    // 15m --script-timeout 10m as well as setting the maximum TCP scan delay to 5 ms.

    public final static String OPTION_FAST_PERFORMANCE = "-T5";

    // --min-hostgroup <numhosts>; --max-hostgroup <numhosts> (Adjust parallel scan group sizes)

    // Nmap has the ability to port scan or version scan multiple hosts in parallel. Nmap does this by dividing the target IP space 
    // into groups and then scanning one group at a time. In general, larger groups are more efficient. The downside is that host 
    // results can't be provided until the whole group is finished. So if Nmap started out with a group size of 50, the user would 
    // not receive any reports (except for the updates offered in verbose mode) until the first 50 hosts are completed.
    // By default, Nmap takes a compromise approach to this conflict. It starts out with a group size as low as five so the first 
    // results come quickly and then increases the groupsize to as high as 1024. The exact default numbers depend on the options 
    // given. For efficiency reasons, Nmap uses larger group sizes for UDP or few-port TCP scans.
    // When a maximum group size is specified with --max-hostgroup, Nmap will never exceed that size. Specify a minimum size with 
    // --min-hostgroup and Nmap will try to keep group sizes above that level. Nmap may have to use smaller groups than you specify 
    // if there are not enough target hosts left on a given interface to fulfill the specified minimum. Both may be set to keep the 
    // group size within a specific range, though this is rarely desired.
    // These options do not have an effect during the host discovery phase of a scan. This includes plain ping scans (-sn). Host 
    // discovery always works in large groups of hosts to improve speed and accuracy.
    //
    // The primary use of these options is to specify a large minimum group size so that the full scan runs more quickly. A common 
    // choice is 256 to scan a network in Class C sized chunks. For a scan with many ports, exceeding that number is unlikely to 
    // help much. For scans of just a few port numbers, host group sizes of 2048 or more may be helpful.

    public final static String[] OPTION_HOSTGROUP = new String[]{"--min-hostgroup", "256", "--max-hostgroup", "256"};

    public final static String OPTION_HOSTGROUP_MIN = "--min-hostgroup";

    public final static String OPTION_HOSTGROUP_MAX = "--max-hostgroup";

    // --max-retries <numtries> (Specify the maximum number of port scan probe retransmissions)

    // When Nmap receives no response to a port scan probe, it could mean the port is filtered. Or maybe the probe or response was 
    // simply lost on the network. It is also possible that the target host has rate limiting enabled that temporarily blocked the 
    // response. So Nmap tries again by retransmitting the initial probe. If Nmap detects poor network reliability, it may try many 
    // more times before giving up on a port. While this benefits accuracy, it also lengthens scan times. When performance is critical, 
    // scans may be sped up by limiting the number of retransmissions allowed. You can even specify --max-retries 0 to prevent any 
    // retransmissions, though that is only recommended for situations such as informal surveys where occasional missed ports and 
    // hosts are acceptable.
    // The default (with no -T template) is to allow ten retransmissions. If a network seems reliable and the target hosts aren't 
    // rate limiting, Nmap usually only does one retransmission. So most target scans aren't even affected by dropping --max-retries 
    // to a low value such as three. Such values can substantially speed scans of slow (rate limited) hosts. You usually lose some 
    // information when Nmap gives up on ports early, though that may be preferable to letting the --host-timeout expire and losing 
    // all information about the target.

    public final static String[] OPTION_RETRIES = new String[]{"--max-retries", "1"};

    public final static String OPTION_RETRIES_MAX = "--max-retries";


    public final static String BANNER = "--script=banner.nse";

    // --traceroute (Trace path to host)

    // Traceroutes are performed post-scan using information from the scan results to determine the port and protocol most likely 
    // to reach the target. It works with all scan types except connect scans (-sT) and idle scans (-sI). All traces use Nmap's 
    // dynamic timing model and are performed in parallel.
    // Traceroute works by sending packets with a low TTL (time-to-live) in an attempt to elicit ICMP Time Exceeded messages from 
    // intermediate hops between the scanner and the target host. Standard traceroute implementations start with a TTL of 1 and 
    // increment the TTL until the destination host is reached. Nmap's traceroute starts with a high TTL and then decrements the 
    // TTL until it reaches zero. Doing it backwards lets Nmap employ clever caching algorithms to speed up traces over multiple 
    // hosts. On average Nmap sends 5–10 fewer packets per host, depending on network conditions. If a single subnet is being scanned (i.e. 192.168.0.0/24) 
    // Nmap may only have to send two packets to most hosts.

    public final static String OPTION_TRACE_ROUTE = "--traceroute";

    // --min-parallelism <numprobes>; --max-parallelism <numprobes> (Adjust probe parallelization)

    // These options control the total number of probes that may be outstanding for a host group. They are used for port scanning 
    // and host discovery. By default, Nmap calculates an ever-changing ideal parallelism based on network performance. If packets 
    // are being dropped, Nmap slows down and allows fewer outstanding probes. The ideal probe number slowly rises as the network 
    // proves itself worthy. These options place minimum or maximum bounds on that variable. By default, the ideal parallelism 
    // can drop to one if the network proves unreliable and rise to several hundred in perfect conditions.
    // The most common usage is to set --min-parallelism to a number higher than one to speed up scans of poorly performing hosts 
    // or networks. This is a risky option to play with, as setting it too high may affect accuracy. Setting this also reduces 
    // Nmap's ability to control parallelism dynamically based on network conditions. A value of 10 might be reasonable, though 
    // I only adjust this value as a last resort.
    // The --max-parallelism option is sometimes set to one to prevent Nmap from sending more than one probe at a time to hosts. 
    // The --scan-delay option, discussed later, is another way to do this.

    public final static String OPTION_PARALLELISM_MIN = "-min-parallelism";

    public final static String OPTION_PARALLELISM_MAX = "-max-parallelism";

    // -sV (Version detection)

    // Enables version detection, as discussed above. Alternatively, you can use -A, which enables version detection among other things.
    // -sR is an alias for -sV. Prior to March 2011, it was used to active the RPC grinder separately from version detection, but 
    // now these options are always combined.

    public final static String OPTION_VERSION_DETECTION = "-sV";

    // -O (Enable OS detection)

    // Enables OS detection, as discussed above. Alternatively, you can use -A to enable OS detection along with other things.

    public final static String OPTION_OS_DETECTION = "-O";

    // --osscan-limit (Limit OS detection to promising targets)

    // OS detection is far more effective if at least one open and one closed TCP port are found. Set this option and Nmap will 
    // not even try OS detection against hosts that do not meet this criteria. This can save substantial time, particularly on 
    // -Pn scans against many hosts. It only matters when OS detection is requested with -O or -A.

    // --osscan-guess; --fuzzy (Guess OS detection results)

    // When Nmap is unable to detect a perfect OS match, it sometimes offers up near-matches as possibilities. The match has to 
    // be very close for Nmap to do this by default. Either of these (equivalent) options make Nmap guess more aggressively. Nmap 
    // will still tell you when an imperfect match is printed and display its confidence level (percentage) for each guess.

    // --max-os-tries (Set the maximum number of OS detection tries against a target)

    // When Nmap performs OS detection against a target and fails to find a perfect match, it usually repeats the attempt. By default, 
    // Nmap tries five times if conditions are favorable for OS fingerprint submission, and twice when conditions aren't so good. 
    // Specifying a lower --max-os-tries value (such as 1) speeds Nmap up, though you miss out on retries which could potentially 
    // identify the OS. Alternatively, a high value may be set to allow even more retries when conditions are favorable. This is 
    // rarely done, except to generate better fingerprints for submission and integration into the Nmap OS database.

    public final static String OPTION_OS_OSSCAN_LIMIT = "--osscan-limit";

    // --script <filename>|<category>|<directory>|<expression>[,...]

    // Runs a script scan using the comma-separated list of filenames, script categories, and directories. Each element in the 
    // list may also be a Boolean expression describing a more complex set of scripts. Each element is interpreted first as an 
    // expression, then as a category, and finally as a file or directory name. The special argument all makes every script in 
    // Nmap's script database eligible to run. The all argument should be used with caution as NSE may contain dangerous scripts 
    // including exploits, brute force authentication crackers, and denial of service attacks.
    // Each element in the script expression list may be prefixed with a + character to force the given script(s) to run regardless 
    // of the conditions in their portrule or hostrule functions. This is generally only done by advanced users in special cases. 
    // For example, you might want to do a configuration review on a bunch of MS SQL servers, some of which are running on nonstandard 
    // ports. Rather than slow the Nmap scan by running extensive version detection (-sV --version-all) so that Nmap will recognize 
    // the ms-sql service, you can force the ms-sql-config script to run against all the targetted hosts and ports by specifying 
    // --script +ms-sql-config.
    // File and directory names may be relative or absolute. Absolute names are used directly. Relative paths are searched for 
    // in the scripts subdirectory of each of the following places until found:

    // --script-args <args>

    // Provides arguments to the scripts. See the section called “Arguments to Scripts” for a detailed explanation.

    // --script-updatedb

    // This option updates the script database found in scripts/script.db which is used by Nmap to determine the available default 
    // scripts and categories. It is only necessary to update the database if you have added or removed NSE scripts from the default 
    // scripts directory or if you have changed the categories of any script. This option is generally used by 
    // itself: nmap --script-updatedb.

    public final static String OPTION_SCRIPT = "--script";

    public final static String OPTION_SCRIPT_ARGS = "--script-args";

    public final static String OPTION_SCRIPT_UPDATE_DB = "--script-updatedb";

    // -Pn (No ping)

    // This option skips the Nmap discovery stage altogether. Normally, Nmap uses this stage to determine active machines for heavier 
    // scanning. By default, Nmap only performs heavy probing such as port scans, version detection, or OS detection against hosts 
    // that are found to be up. Disabling host discovery with -Pn causes Nmap to attempt the requested scanning functions against 
    // every target IP address specified. So if a class B target address space (/16) is specified on the command line, all 65,536 
    // IP addresses are scanned. Proper host discovery is skipped as with the list scan, but instead of stopping and printing the 
    // target list, Nmap continues to perform requested functions as if each target IP is active. To skip ping scan and port scan, 
    // while still allowing NSE to run, use the two options -Pn -sn together.
    // For machines on a local ethernet network, ARP scanning will still be performed (unless --disable-arp-ping or --send-ip is 
    // specified) because Nmap needs MAC addresses to further scan target hosts. In previous versions of Nmap, -Pn was -P0 and -PN.

    public final static String OPTION_DISABLE_PING = "-Pn";

    // -sn (No port scan)
    // This option tells Nmap not to do a port scan after host discovery, and only print out the available hosts that responded to 
    // the host discovery probes. This is often known as a “ping scan”, but you can also request that traceroute and NSE host scripts 
    // be run. This is by default one step more intrusive than the list scan, and can often be used for the same purposes. It allows 
    // light reconnaissance of a target network without attracting much attention. Knowing how many hosts are up is more valuable 
    // to attackers than the list provided by list scan of every single IP and host name.
    // Systems administrators often find this option valuable as well. It can easily be used to count available machines on a network 
    // or monitor server availability. This is often called a ping sweep, and is more reliable than pinging the broadcast address because 
    // many hosts do not reply to broadcast queries.
    // The default host discovery done with -sn consists of an ICMP echo request, TCP SYN to port 443, TCP ACK to port 80, and an 
    // ICMP timestamp request by default. When executed by an unprivileged user, only SYN packets are sent (using a connect call) 
    // to ports 80 and 443 on the target. When a privileged user tries to scan targets on a local ethernet network, ARP requests 
    // are used unless --send-ip was specified. The -sn option can be combined with any of the discovery probe types (the -P* options, excluding -Pn) 
    // for greater flexibility. If any of those probe type and port number options are used, the default probes are overridden. 
    // When strict firewalls are in place between the source host running Nmap and the target network, using those advanced techniques 
    // is recommended. Otherwise hosts could be missed when the firewall drops probes or their responses.
    // In previous releases of Nmap, -sn was known as -sP.

    public final static String OPTION_DISABLE_PORT_SCAN = "-sn";

    // -PO <protocol list> (IP Protocol Ping)
    // One of the newer host discovery options is the IP protocol ping, which sends IP packets with the specified protocol number
    // set in their IP header. The protocol list takes the same format as do port lists in the previously discussed TCP, UDP and
    // SCTP host discovery options. If no protocols are specified, the default is to send multiple IP packets for ICMP (protocol 1),
    // IGMP (protocol 2), and IP-in-IP (protocol 4). The default protocols can be configured at compile-time by changing DEFAULT_PROTO_PROBE_PORT_SPEC
    // in nmap.h. Note that for the ICMP, IGMP, TCP (protocol 6), UDP (protocol 17) and SCTP (protocol 132), the packets are sent 
    // with the proper protocol headers while other protocols are sent with no additional data beyond the IP header (unless any of 
    // --data, --data-string, or --data-length options are specified).
    // This host discovery method looks for either responses using the same protocol as a probe, or ICMP protocol unreachable messages 
    // which signify that the given protocol isn't supported on the destination host. Either type of response signifies that the 
    // target host is alive.

    public final static String OPTION_PROTOCOL_PING = "-P0";

    // -p <port ranges> (Only scan specified ports)

    // This option specifies which ports you want to scan and overrides the default. Individual port numbers are OK, as are ranges 
    // separated by a hyphen (e.g. 1-1023). The beginning and/or end values of a range may be omitted, causing Nmap to use 1 and 
    // 65535, respectively. So you can specify -p- to scan ports from 1 through 65535. Scanning port zero is allowed if you specify 
    // it explicitly. For IP protocol scanning (-sO), this option specifies the protocol numbers you wish to scan for (0–255).
    // When scanning a combination of protocols (e.g. TCP and UDP), you can specify a particular protocol by preceding the port 
    // numbers by T: for TCP, U: for UDP, S: for SCTP, or P: for IP Protocol. The qualifier lasts until you specify another qualifier. 
    // For example, the argument -p U:53,111,137,T:21-25,80,139,8080 would scan UDP ports 53, 111,and 137, as well as the listed 
    // TCP ports. Note that to scan both UDP and TCP, you have to specify -sU and at least one TCP scan type (such as -sS, -sF, 
    // or -sT). If no protocol qualifier is given, the port numbers are added to all protocol lists.
    // Ports can also be specified by name according to what the port is referred to in the nmap-services. You can even use the 
    // wildcards * and ? with the names. For example, to scan FTP and all ports whose names begin with “http”, use -p ftp,http*. 
    // Be careful about shell expansions and quote the argument to -p if unsure.
    // Ranges of ports can be surrounded by square brackets to indicate ports inside that range that appear in nmap-services. For 
    // example, the following will scan all ports in nmap-services equal to or below 1024: -p [-1024]. Be careful with shell expansions 
    // and quote the argument to -p if unsure.

    public final static String OPTION_PORT = "-p";

    // --stats-every <time> (Print periodic timing stats)
    // Periodically prints a timing status message after each interval of <time>. The time is a specification of the kind described 
    // in the section called “Timing and Performance”; so for example, use --stats-every 10s to get a status update every 10 seconds. 
    // Updates are printed to interactive output (the screen) and XML output.

    public final static String[] OPTION_PROGRESS = new String[]{"--stats-every", "1s"};

    // -n (No DNS resolution)

    // Tells Nmap to never do reverse DNS resolution on the active IP addresses it finds. Since DNS can be slow even with Nmap's built-in 
    // parallel stub resolver, this option can slash scanning times.

    public final static String OPTION_DISABLE_DNS_RESOLUTION = "-n";

    // -A (Aggressive scan options)

    // This option enables additional advanced and aggressive options. Presently this enables OS detection (-O), version scanning (-sV), 
    // script scanning (-sC) and traceroute (--traceroute). More features may be added in the future. The point is to enable a comprehensive
    // set of scan options without people having to remember a large set of flags. However, because script scanning with the default set is
    // considered intrusive, you should not use -A against target networks without permission. This option only enables features, and not 
    // timing options (such as -T4) or verbosity options (-v) that you might want as well. Options which require privileges (e.g. root access)
    // such as OS detection and traceroute will only be enabled if those privileges are available.

    public final static String OPTION_AGGRESS = "-A";

    public final static String OPTION_DETAIL = "-d";

    // -e <iface>: Use specified interface

    // Tells Nmap what interface to send and receive packets on. Nmap should be able to detect this automatically, but it will 
    // tell you if it cannot.

    public final static String OPTION_SPECIFIED_INTERFACE = "-e";

    // --datadir <directoryname> (Specify custom Nmap data file location)

    // Nmap obtains some special data at runtime in files named nmap-service-probes, nmap-services, nmap-protocols, nmap-rpc,
    // nmap-mac-prefixes, and nmap-os-db. If the location of any of these files has been specified (using the --servicedb or 
    // --versiondb options), that location is used for that file. After that, Nmap searches these files in the directory specified 
    // with the --datadir option (if any). Any files not found there, are searched for in the directory specified by the NMAPDIR 
    // environment variable. Next comes ~/.nmap for real and effective UIDs; or on Windows, <HOME>/AppData/Roaming/nmap (where <HOME> 
    // is the user's home directory, like C:\\Users\\user). This is followed by the location of the nmap executable and the same 
    // location with ../share/nmap appended. Then a compiled-in location such as /usr/local/share/nmap or /usr/share/nmap.

    public final static String OPTION_DATA_DIRECTORY = "--datadir";

    // ------------------------------------------------------------------------

    private NmapCommandBuilder() {
    }

    public static CommandSteps steps() {
        return steps(true);
    }

    public static CommandSteps steps(boolean rooted) {
        return new CommandSteps(rooted);
    }

    // ------------------------------------------------------------------------

    public static class CommandSteps implements // 
            HostDiscoveryStep, ScanTechniquesStep, //
            PortSpecificationAndScanOrderStep, ServiceOrVersionDetectionStep, //
            ScriptScanStep, OsDetectionStep, //
            TimingAndPerformanceStep, FirewallOrIdsEvasionAndSpoofingStep, //
            OutputStep, MiscStep, //
            TargetSpecificationStep {

        private CommandSteps(boolean rooted) {
            if (rooted) this.commandLine = new CommandLine("sudo").addArgument(NMAP_EXEC_FILE.getAbsolutePath());
            else this.commandLine = new CommandLine(NMAP_EXEC_FILE);
        }

        private final CommandLine commandLine;

        @Override
        public CommandLine target(InetStyeSegment... segments) {
            for (InetStyeSegment segment : segments) {
                commandLine.addArgument(segment.netmark());
            }
            return commandLine;
        }

        @Override
        public CommandLine target(InetStyeAddress... addresses) {
            for (InetStyeAddress address : addresses) {
                commandLine.addArgument(address.netmark());
            }
            return commandLine;
        }

        @Override
        public CommandLine target(boolean withBanner, String... netmarks) {
            if (withBanner) {
                commandLine.addArgument(BANNER);
            }
            for (String netmark : netmarks) {
                commandLine.addArgument(netmark);
            }
            return commandLine;
        }

        @Override
        public CommandLine targetFromFile(File file) {
            throw new UnsupportedOperationException();
        }

        @Override
        public TargetSpecificationStep finishMiscStep() {
            return this;
        }

        @Override
        public MiscStep dataDirectory(File file) {
            commandLine.addArgument(OPTION_DATA_DIRECTORY).addArgument(file.getPath());
            return this;
        }

        @Override
        public MiscStep finishOutputStep() {
            commandLine.addArguments(OPTION_OUTPUT_XML);
            return this;
        }

        @Override
        public MiscStep onlyShowOpen() {
            commandLine.addArgument(OPTION_OPEN);
            return this;
        }

        @Override
        public MiscStep debuggingLevel() {
            commandLine.addArgument(OPTION_DETAIL);
            return this;
        }

        @Override
        public OutputStep finishFirewallOrIdsEvasionAndSpoofingStep() {
            return this;
        }

        @Override
        public FirewallOrIdsEvasionAndSpoofingStep finishTimingAndPerformanceStep() {
            return this;
        }

        @Override
        public OutputStep specifiedInterface(String value) {
            if (!StringUtils.isBlank(value))
                commandLine.addArgument(OPTION_SPECIFIED_INTERFACE).addArgument(value);
            return this;
        }

        @Override
        public TimingAndPerformanceStep template(int level) {
            if (level >= 0 && level <= 5) commandLine.addArgument(String.format("-T%d", level));
            return this;
        }

        @Override
        public TimingAndPerformanceStep parallelism(Integer lower, Integer upper) {
            if (lower != null && upper != null) {
                commandLine.addArgument(OPTION_PARALLELISM_MIN).addArgument(Integer.toString(lower));
                commandLine.addArgument(OPTION_PARALLELISM_MAX).addArgument(Integer.toString(upper));
            }
            return this;
        }

        @Override
        public TimingAndPerformanceStep hostgroup(Integer lower, Integer upper) {
            if (lower != null && upper != null) {
                commandLine.addArgument(OPTION_HOSTGROUP_MIN).addArgument(Integer.toString(lower));
                commandLine.addArgument(OPTION_HOSTGROUP_MAX).addArgument(Integer.toString(upper));
            }
            return this;
        }

        @Override
        public TimingAndPerformanceStep retries(Integer retries) {
            if (retries != null) commandLine.addArgument(OPTION_RETRIES_MAX).addArgument(Integer.toString(retries));
            return this;
        }

        @Override
        public TimingAndPerformanceStep finishOsDetectionStep() {
            return this;
        }

        @Override
        public TimingAndPerformanceStep scanOperationSystem(boolean value) {
            if (value) commandLine.addArgument(OPTION_OS_DETECTION);
            return this;
        }

        @Override
        public OsDetectionStep finishScriptScanStep() {
            return this;
        }

        @Override
        public ScriptScanStep scanScripts(String[] scripts) {
            if (scripts.length > 0) commandLine.addArgument(OPTION_SCRIPT).addArgument(StringUtils.join(scripts, ","));
            return this;
        }

        @Override
        public ScriptScanStep scanScriptArguments(String[] scriptArguments) {
            if (scriptArguments.length > 0)
                commandLine.addArgument(OPTION_SCRIPT_ARGS).addArgument(StringUtils.join(scriptArguments, ","));
            return this;
        }

        @Override
        public ScriptScanStep scanScriptUpdateDatabase(boolean value) {
            if (value) commandLine.addArgument(OPTION_SCRIPT_UPDATE_DB);
            return this;
        }

        @Override
        public OsDetectionStep scanScriptCategories(String[] scriptCategories) {
            if (scriptCategories.length == 0) return this;
            List<String> scripts = new ArrayList<>();
            List<String> scriptArguments = new ArrayList<>();

            List<NmapScanCategory> categoryList = new ArrayList<>();
            for (String category : scriptCategories) {
                categoryList.add(NmapScanCategory.valueOf(category));
            }

            for (NmapScanCategory category : categoryList) {
                scripts.add(category.script());
                scriptArguments.addAll(category.scriptArguments());
            }
            if (!scripts.isEmpty()) commandLine.addArgument(OPTION_SCRIPT).addArgument(StringUtils.join(scripts, ","));
            if (!scriptArguments.isEmpty())
                commandLine.addArgument(OPTION_SCRIPT_ARGS).addArgument(StringUtils.join(scriptArguments, ","));
            return this;
        }

        @Override
        public ScriptScanStep finishServiceOrVersionDetectionStep() {
            return this;
        }

        @Override
        public ScriptScanStep scanServiceVersion(boolean value) {
            if (value) commandLine.addArgument(OPTION_VERSION_DETECTION);
            return this;
        }

        @Override
        public ServiceOrVersionDetectionStep finishPortSpecificationAndScanOrderStep() {
            return this;
        }

        @Override
        public ServiceOrVersionDetectionStep port(String portmark) {
            if (!StringUtils.isBlank(portmark)) commandLine.addArgument(OPTION_PORT).addArgument(portmark);
            return this;
        }

        @Override
        public ServiceOrVersionDetectionStep ports(String[] portmark) {
            if (portmark.length > 0) commandLine.addArgument(OPTION_PORT).addArgument(StringUtils.join(portmark, ","));
            else commandLine.addArgument(OPTION_PORT).addArgument(OPTION_VERBOSE_PORTMARK);
            return this;
        }

        @Override
        public PortSpecificationAndScanOrderStep finishScanTechniquesStep() {
            return this;
        }

        @Override
        public ScanTechniquesStep scanTechnique(NmapScanTechnique value) {
            if (value != null) commandLine.addArgument(value.option());
            return this;
        }

        @Override
        public ScanTechniquesStep finishHostDiscoveryStep() {
            return this;
        }

        @Override
        public HostDiscoveryStep disablePortScan(boolean value) {
            if (value) commandLine.addArgument(OPTION_DISABLE_PORT_SCAN);
            return this;
        }

        @Override
        public HostDiscoveryStep disableHostDiscovery(boolean value) {
            if (value) commandLine.addArgument(OPTION_DISABLE_PING);
            return this;
        }

        @Override
        public HostDiscoveryStep disableDnsResolution(boolean value) {
            if (value) commandLine.addArgument(OPTION_DISABLE_DNS_RESOLUTION);
            return this;
        }

        @Override
        public HostDiscoveryStep traceRoute(boolean value) {
            if (value) commandLine.addArgument(OPTION_TRACE_ROUTE);
            return this;
        }

    }

    // ------------------------------------------------------------------------

    // HOST DISCOVERY:
    //   -sL: List Scan - simply list targets to scan
    //   -sn: Ping Scan - disable port scan
    //   -Pn: Treat all hosts as online -- skip host discovery
    //   -PS/PA/PU/PY[portlist]: TCP SYN/ACK, UDP or SCTP discovery to given ports
    //   -PE/PP/PM: ICMP echo, timestamp, and netmask request discovery probes
    //   -PO[protocol list]: IP Protocol Ping
    //   -n/-R: Never do DNS resolution/Always resolve [default: sometimes]
    //   --dns-servers <serv1[,serv2],...>: Specify custom DNS servers
    //   --system-dns: Use OS's DNS resolver
    //   --traceroute: Trace hop path to each host

    public interface HostDiscoveryStep {

        ScanTechniquesStep finishHostDiscoveryStep();

        HostDiscoveryStep disablePortScan(boolean value);

        HostDiscoveryStep disableHostDiscovery(boolean value);

        HostDiscoveryStep disableDnsResolution(boolean value);

        HostDiscoveryStep traceRoute(boolean value);
    }

    // SCAN TECHNIQUES:
    //   -sS/sT/sA/sW/sM: TCP SYN/Connect()/ACK/Window/Maimon scans
    //   -sU: UDP Scan
    //   -sN/sF/sX: TCP Null, FIN, and Xmas scans
    //   --scanflags <flags>: Customize TCP scan flags
    //   -sI <zombie host[:probeport]>: Idle scan
    //   -sY/sZ: SCTP INIT/COOKIE-ECHO scans
    //   -sO: IP protocol scan
    //   -b <FTP relay host>: FTP bounce scan

    public interface ScanTechniquesStep {

        PortSpecificationAndScanOrderStep finishScanTechniquesStep();

        ScanTechniquesStep scanTechnique(NmapScanTechnique value);

    }

    // PORT SPECIFICATION AND SCAN ORDER:
    //   -p <port ranges>: Only scan specified ports
    //     Ex: -p22; -p1-65535; -p U:53,111,137,T:21-25,80,139,8080,S:9
    //   --exclude-ports <port ranges>: Exclude the specified ports from scanning
    //   -F: Fast mode - Scan fewer ports than the default scan
    //   -r: Scan ports consecutively - don't randomize
    //   --top-ports <number>: Scan <number> most common ports
    //   --port-ratio <ratio>: Scan ports more common than <ratio>

    public interface PortSpecificationAndScanOrderStep {

        ServiceOrVersionDetectionStep finishPortSpecificationAndScanOrderStep();

        ServiceOrVersionDetectionStep port(String portmark);

        ServiceOrVersionDetectionStep ports(String[] portmark);

    }

    // SERVICE/VERSION DETECTION:
    //   -sV: Probe open ports to determine service/version info
    //   --version-intensity <level>: Set from 0 (light) to 9 (try all probes)
    //   --version-light: Limit to most likely probes (intensity 2)
    //   --version-all: Try every single probe (intensity 9)
    //   --version-trace: Show detailed version scan activity (for debugging)

    public interface ServiceOrVersionDetectionStep {

        ScriptScanStep finishServiceOrVersionDetectionStep();

        ScriptScanStep scanServiceVersion(boolean value);

    }

    // SCRIPT SCAN:
    //   -sC: equivalent to --script=default
    //   --script=<Lua scripts>: <Lua scripts> is a comma separated list of
    //            directories, script-files or script-categories
    //   --script-args=<n1=v1,[n2=v2,...]>: provide arguments to scripts
    //   --script-args-file=filename: provide NSE script args in a file
    //   --script-trace: Show all data sent and received
    //   --script-updatedb: Update the script database.
    //   --script-help=<Lua scripts>: Show help about scripts.
    //            <Lua scripts> is a comma-separated list of script-files or
    //            script-categories.

    public interface ScriptScanStep {

        OsDetectionStep finishScriptScanStep();

        ScriptScanStep scanScripts(String[] scripts);

        ScriptScanStep scanScriptArguments(String[] scriptArguments);

        ScriptScanStep scanScriptUpdateDatabase(boolean value);

        OsDetectionStep scanScriptCategories(String[] scriptCategories);

    }

    // OS DETECTION:
    //   -O: Enable OS detection
    //   --osscan-limit: Limit OS detection to promising targets
    //   --osscan-guess: Guess OS more aggressively

    public interface OsDetectionStep {

        TimingAndPerformanceStep finishOsDetectionStep();

        TimingAndPerformanceStep scanOperationSystem(boolean value);

    }

    // TIMING AND PERFORMANCE:
    //   Options which take <time> are in seconds, or append 'ms' (milliseconds),
    //   's' (seconds), 'm' (minutes), or 'h' (hours) to the value (e.g. 30m).
    //   -T<0-5>: Set timing template (higher is faster)
    //   --min-hostgroup/max-hostgroup <size>: Parallel host scan group sizes
    //   --min-parallelism/max-parallelism <numprobes>: Probe parallelization
    //   --min-rtt-timeout/max-rtt-timeout/initial-rtt-timeout <time>: Specifies
    //       probe round trip time.
    //   --max-retries <tries>: Caps number of port scan probe retransmissions.
    //   --host-timeout <time>: Give up on target after this long
    //   --scan-delay/--max-scan-delay <time>: Adjust delay between probes
    //   --min-rate <number>: Send packets no slower than <number> per second
    //   --max-rate <number>: Send packets no faster than <number> per second

    public interface TimingAndPerformanceStep {

        FirewallOrIdsEvasionAndSpoofingStep finishTimingAndPerformanceStep();

        TimingAndPerformanceStep template(int level);

        TimingAndPerformanceStep parallelism(Integer lower, Integer upper);

        TimingAndPerformanceStep hostgroup(Integer lower, Integer upper);

        TimingAndPerformanceStep retries(Integer retries);

    }

    // FIREWALL/IDS EVASION AND SPOOFING:
    //   -f; --mtu <val>: fragment packets (optionally w/given MTU)
    //   -D <decoy1,decoy2[,ME],...>: Cloak a scan with decoys
    //   -S <IP_Address>: Spoof source address
    //   -e <iface>: Use specified interface
    //   -g/--source-port <portnum>: Use given port number
    //   --proxies <url1,[url2],...>: Relay connections through HTTP/SOCKS4 proxies
    //   --data <hex string>: Append a custom payload to sent packets
    //   --data-string <string>: Append a custom ASCII string to sent packets
    //   --data-length <num>: Append random data to sent packets
    //   --ip-options <options>: Send packets with specified ip options
    //   --ttl <val>: Set IP time-to-live field
    //   --spoof-mac <mac address/prefix/vendor name>: Spoof your MAC address
    //   --badsum: Send packets with a bogus TCP/UDP/SCTP checksum

    public interface FirewallOrIdsEvasionAndSpoofingStep {

        OutputStep specifiedInterface(String value);

        OutputStep finishFirewallOrIdsEvasionAndSpoofingStep();

    }

    // OUTPUT:
    //   -oN/-oX/-oS/-oG <file>: Output scan in normal, XML, s|<rIpt kIddi3,
    //      and Grepable format, respectively, to the given filename.
    //   -oA <basename>: Output in the three major formats at once
    //   -v: Increase verbosity level (use -vv or more for greater effect)
    //   -d: Increase debugging level (use -dd or more for greater effect)
    //   --reason: Display the reason a port is in a particular state
    //   --open: Only show open (or possibly open) ports
    //   --packet-trace: Show all packets sent and received
    //   --iflist: Print host interfaces and routes (for debugging)
    //   --append-output: Append to rather than clobber specified output files
    //   --resume <filename>: Resume an aborted scan
    //   --stylesheet <path/URL>: XSL stylesheet to transform XML output to HTML
    //   --webxml: Reference stylesheet from Nmap.Org for more portable XML
    //   --no-stylesheet: Prevent associating of XSL stylesheet w/XML output

    public interface OutputStep {

        MiscStep onlyShowOpen();

        MiscStep debuggingLevel();

        MiscStep finishOutputStep();

    }

    // MISC:
    //   -6: Enable IPv6 scanning
    //   -A: Enable OS detection, version detection, script scanning, and traceroute
    //   --datadir <dirname>: Specify custom Nmap data file location
    //   --send-eth/--send-ip: Send using raw ethernet frames or IP packets
    //   --privileged: Assume that the user is fully privileged
    //   --unprivileged: Assume the user lacks raw socket privileges
    //   -V: Print version number
    //   -h: Print this help summary page.

    public interface MiscStep {

        MiscStep dataDirectory(File file);

        TargetSpecificationStep finishMiscStep();

    }

    // TARGET SPECIFICATION:
    //   Can pass hostnames, IP addresses, networks, etc.
    //   Ex: scanme.nmap.org, microsoft.com/24, 192.168.0.1; 10.0.0-255.1-254
    //   -iL <inputfilename>: Input from list of hosts/networks
    //   -iR <num hosts>: Choose random targets
    //   --exclude <host1[,host2][,host3],...>: Exclude hosts/networks
    //   --excludefile <exclude_file>: Exclude list from file

    public interface TargetSpecificationStep {

        CommandLine target(boolean withBanner, String... netmarks);

        CommandLine target(InetStyeSegment... segments);

        CommandLine target(InetStyeAddress... addresses);

        CommandLine targetFromFile(File file);

    }

}

package com.jia.jnmap.utils.nmap;

import com.jia.jnmap.exec.NmapOption;
import org.apache.commons.exec.CommandLine;

import java.util.List;

/**
 * @author xutao@gaffassen.com
 * @version 1.0.0
 * @date 2020-03-14 09:55
 */
public class NmapCommandUtil {

    // nmap安装路径
    private static final String NMAP_DIR = "/usr/bin/nmap";

    /**
     * 构建nmap扫描指令
     * @param sudo      是否用root用户启动
     * @param options   扫描选项
     * @param targets   扫描目标
     */
    public static CommandLine buildCommand(boolean sudo, List<NmapOption> options, String... targets) {
        CommandLine commandLine = sudo ? new CommandLine("sudo").addArgument(NMAP_DIR) : new CommandLine(NMAP_DIR);

        // 扫描选项
        for (NmapOption option : options) {
            if (option == null || option.getValue() == null) continue;

            // 指定扫描选项
            for (String arg : option.getValue()) {
                commandLine.addArgument(arg);
            }
        }
        // 扫描目标
        for (String target : targets) {
            commandLine.addArgument(target);
        }
        return commandLine;
    }

}

///*****************************************************************************
// * PROJECT: SAPPO PROJECT.
// * SUPPLIER: SAPPO TEAM.
// *****************************************************************************
// * FILE: EmbedCommandUtils.java
// * (C) Copyright Scout Team 2017, All Rights Reserved.
// *****************************************************************************/
//package com.jia.jnmap.utils;
//
//import com.gaff.staff.vuln.VulnerabilityUtils;
//import org.apache.commons.io.FileUtils;
//import org.apache.ibatis.jdbc.ScriptRunner;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import java.io.InputStreamReader;
//import java.io.PrintStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.util.Locale;
//
//
///**
// * @author liuzheng@gcsoftware.com
// * @version 1.0.0
// */
//public class EmbedCommandUtils {
//
//    // ------------------------------------------------------------------------
//
//    public static void showLogo(PrintStream stdout) {
//        stdout.println();
//        stdout.println(" ██████╗  █████╗ ███████╗███████╗    ███████╗ ██████╗ ██████╗ ██╗   ██╗████████╗");
//        stdout.println("██╔════╝ ██╔══██╗██╔════╝██╔════╝    ██╔════╝██╔════╝██╔═══██╗██║   ██║╚══██╔══╝");
//        stdout.println("██║  ███╗███████║█████╗  █████╗      ███████╗██║     ██║   ██║██║   ██║   ██║   ");
//        stdout.println("██║   ██║██╔══██║██╔══╝  ██╔══╝      ╚════██║██║     ██║   ██║██║   ██║   ██║   ");
//        stdout.println("╚██████╔╝██║  ██║██║     ██║         ███████║╚██████╗╚██████╔╝╚██████╔╝   ██║   ");
//        stdout.println(" ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝         ╚══════╝ ╚═════╝ ╚═════╝  ╚═════╝    ╚═╝   ");
//        stdout.println();
//    }
//
//    public static void showVersion(PrintStream stdout) {
//        stdout.println("################################################################################");
//        stdout.println("#                             GAFF SCOUT PROJECT                               #");
//        stdout.println("################################################################################");
//        stdout.format("    Sappo version: %s\n", "1.1.x");
//        stdout.format("     Sappo vendor: %s\n", "Hunan Gaff Assen Technology Co.Ltd");
//        stdout.format("       Sappo home: %s\n", System.getProperty(Environment.PROPERTY_HOME, "<unknown sappo home>"));
//        stdout.format("     Java version: %s\n", System.getProperty("java.version", "<unknown java version>"));
//        stdout.format("      Java vendor: %s\n", System.getProperty("java.vendor", "<unknown vendor>"));
//        stdout.format("        Java home: %s\n", System.getProperty("java.home", "<unknown java home>"));
//        stdout.format("   Default locale: %s\n", Locale.getDefault().toString());
//        stdout.format("Platform encoding: %s\n", System.getProperty("file.encoding", "<unknown encoding>"));
//    }
//
//    private static Scheduler scheduler;
//
//    public static void startup(PrintStream stdout, boolean reset) throws Exception {
//        showLogo(stdout);
//        if (reset) Environment.reset(); // TODO(每次启动时重置数据)
//        Environment.logback(); // 日志配置初始化
//        Environment.saveByCurrentProcessId(); // 保存进程 PID 记录
//        Runtime.getRuntime().addShutdownHook(new ShutdownThread(stdout)); // 设置退出函数回调
//        boolean inited = !Environment.checkDatabase() || !Environment.checkLoggingDatabase(); // 如果未发现数据库，则初始化数据库
//        if (inited) Environment.configureDatabase("gaffsec", "localhost"); // 数据库初始化
//        if (inited) Environment.configureLoggingDatabase("gaffsec", "localhost"); // 日志数据库初始化
//
//        configureV3Database();
//
//        Environment.springWithDefault();
//        if (inited) UserProfile.initialize(); // 用户初始化
//        if (inited) Strategy.initialize(); // 安全策略初始化
//        if (inited) SurveyTemplates.initialize(); // 扫描模板初始化
//        if (inited) EmailConfigs.initialize(); // 邮箱配置初始化
//        if (inited) SystemConfig.initialize(); // 系统配置初始化
//        AssetUtils.scheduleStatistics(); // 统计任务初始化
//        scheduler = VulnerabilityInfo.startReportJob();
//        EmbedServices.manage().startAsync().awaitStopped();
//    }
//
//    private static void configureV3Database() throws Exception {
//        Class.forName("org.h2.Driver");
//        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:/database-grammers-v3/*.sql");
//        Connection connection = DriverManager.getConnection(String.format("jdbc:h2:%s/data/%s-v3;MVCC=TRUE", PROJECT_PATH, PROJECT_NAME), "gaffsec", "localhost");
//        try {
//            ScriptRunner runner = new ScriptRunner(connection);
//            runner.setSendFullScript(true);
//            runner.setAutoCommit(true);
//            runner.setLogWriter(null);
//            for (Resource resource : resources) {
//                runner.runScript(new InputStreamReader(resource.getInputStream()));
//            }
//        } finally {
//            if (connection != null) {
//                connection.close();
//            }
//        }
//    }
//
//    public static void shutdown(PrintStream stdout) throws Exception {
//        stdout.println();
//        stdout.println("################################################################################");
//        stdout.println("#                             GAFF SCOUT PROJECT                               #");
//        stdout.println("################################################################################");
//        stdout.println();
//        if (null != scheduler) {
//            VulnerabilityInfo.stopReportJob(scheduler);
//        }
//        Environment.killByCurrentProcessId();
//
////        VulnerabilityInfo.shutdownJobs(scheduler);
//        stdout.println();
//    }
//
//    public static void updateVulnerabilities(PrintStream stdout, String value) throws Exception {
//        showLogo(stdout);
//        Environment.logback(); // 日志配置初始化
//        stdout.println("正在同步漏洞库...");
//        VulnerabilityUtils.clean();
//        VulnerabilityUtils.readAndStore(FileUtils.getFile(value).toPath());
//    }
//
//    // ------------------------------------------------------------------------
//
//    private static class ShutdownThread extends Thread {
//
//        private final PrintStream stdout;
//
//        private ShutdownThread(PrintStream stdout) {
//            super("SAPPO-SHUTDOWN-THREAD");
//            this.stdout = stdout;
//        }
//
//        @Override
//        public void run() {
//            try {
//                EmbedCommandUtils.shutdown(stdout);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//}

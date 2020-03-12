/*****************************************************************************
 * PROJECT: SCOUT PROJECT.
 * SUPPLIER: SCOUT TEAM.
 *****************************************************************************
 * FILE: ProcessedNmapEngine.java
 * (C) Copyright Scout Team 2017, All Rights Reserved.
 *****************************************************************************/
package com.jia.jnmap.exec;

import org.apache.commons.exec.*;
import org.apache.commons.io.input.NullInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author liuzheng@gcsoftware.com
 * @version 1.0.0
 */
public class NotifiableNmapEngine {

    // ------------------------------------------------------------------------

    private static final Logger logger = LoggerFactory.getLogger(NotifiableNmapEngine.class);

    // ------------------------------------------------------------------------

    // liuzheng@gcsoftware.com:

    // 此处 Watchdog 超时时间设置为无限： INFINITE_TIMEOUT ，由于存在子进程且 Java 无法关闭，超过固定时常则会
    // 出现死锁。
    private final Watchdog watchdog = new Watchdog(ExecuteWatchdog.INFINITE_TIMEOUT);

    // ------------------------------------------------------------------------

    // liuzheng@gcsoftware.com: 

    // 此处执行非常缓慢，其原因由于 process 缓冲区有限，当程序输出文本量大时，容易导致缓冲区阻塞直接死锁，换
    // 成 Apache Commons Exec 插件尝试；

    public InputStream execute(CommandLine command) throws IOException {
        logger.info(command.toString());
        ShutdownHook.getInstance().addWatchdog(watchdog);
        try ( //
              ByteArrayOutputStream os = new ByteArrayOutputStream(1024 * 128); //
              ByteArrayOutputStream es = new ByteArrayOutputStream(); //
        ) {
            ExecuteStreamHandler esh = new PumpStreamHandler(os, es);
            Executor executor = new DefaultExecutor();
            executor.setExitValue(0);
            executor.setStreamHandler(esh);
            executor.setWatchdog(watchdog);
            int exitValue = executor.execute(command);
            if (exitValue == 0) {
                return new ByteArrayInputStream(os.toByteArray());
            } else {
                logger.warn("Error! cxecutor exit code: {}", exitValue);
                logger.warn(es.toString("UTF-8"));
                throw new ExecuteException("Error! exec runned failed.", exitValue);
            }
        } catch (ExecuteException e) {
            if (e.getExitValue() == 143) return new NullInputStream(0);
            throw e;
        } finally {
            ShutdownHook.getInstance().removeWatchdog(watchdog);
        }
    }

    public void destory() throws Exception {
        watchdog.destory();
    }

    // ------------------------------------------------------------------------

    // liuzheng@gcsoftware.com: 此处屏蔽进程进度监控。

    // static class NmapStreamHandler extends PumpStreamHandler {
    //
    //     private final Surveyable survey;
    //
    //     NmapStreamHandler(Surveyable survey, OutputStream out, OutputStream err) {
    //         super(out, err);
    //         this.survey = survey;
    //     }
    //
    //     @Override
    //     protected Thread createPump(InputStream is, OutputStream os, boolean closeWhenExhausted) {
    //         final Thread result = new Thread(new NmapStreamPumper(survey, is, os, closeWhenExhausted), "Exec Stream Pumper");
    //         result.setDaemon(true);
    //         return result;
    //     }
    //
    // }

    // ------------------------------------------------------------------------

    // static class NmapStreamPumper implements Runnable {
    //
    //     private static final Pattern PATTERN_PROGRESS = Pattern.compile("task=\"([^\"]+)\".*percent=\"([\\d\\.]+)\"");
    //
    //     private final Surveyable survey;
    //
    //     private final InputStream is;
    //
    //     private final OutputStream os;
    //
    //     private boolean finished;
    //
    //     private final boolean closeWhenExhausted;
    //
    //     NmapStreamPumper(final Surveyable survey, final InputStream is, final OutputStream os, final boolean closeWhenExhausted) {
    //         this.is = is;
    //         this.os = os;
    //         this.survey = survey;
    //         this.closeWhenExhausted = closeWhenExhausted;
    //     }
    //
    //     public void run() {
    //         synchronized (this) {
    //             finished = false;
    //         }
    //         try {
    //             BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    //             String line;
    //             while ((line = reader.readLine()) != null) {
    //                 logger.debug(line);
    //                 if (line.startsWith("<taskprogress")) {
    //                     Matcher matcher = PATTERN_PROGRESS.matcher(line);
    //                     if (matcher.find()) survey.notifyProgress(matcher.group(1), Floats.tryParse(matcher.group(2)));
    //                 } else os.write(line.getBytes());
    //             }
    //         } catch (final Exception e) {} finally {
    //             if (closeWhenExhausted) {
    //                 try {
    //                     os.close();
    //                 } catch (final IOException e) {
    //                     final String msg = "Got exception while closing exhausted output stream";
    //                     DebugUtils.handleException(msg, e);
    //                 }
    //             }
    //             synchronized (this) {
    //                 finished = true;
    //                 notifyAll();
    //             }
    //         }
    //     }
    //
    //     public synchronized boolean isFinished() {
    //         return finished;
    //     }
    //
    //     public synchronized void waitFor() throws InterruptedException {
    //         while (!isFinished()) {
    //             wait();
    //         }
    //     }
    //
    // }

}

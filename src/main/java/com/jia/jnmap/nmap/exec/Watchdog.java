package com.jia.jnmap.nmap.exec;

import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.OS;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public class Watchdog extends ExecuteWatchdog {

    private Process process;

    Watchdog(long timeout) {
        super(timeout);
    }

    @Override
    public synchronized void start(Process processToMonitor) {
        super.start(processToMonitor);
        process = processToMonitor;
    }

    public long getProcessId() {
        synchronized (this) {
            String name = process.getClass().getName();
            if (!"java.lang.UNIXProcess".equals(name)) return -1;
            try {
                Field field = process.getClass().getDeclaredField("pid");
                field.setAccessible(true);
                long pid = field.getLong(process);
                field.setAccessible(false);
                return pid;
            } catch (Exception e) {
                return -1;
            }
        }
    }

    public void destory() throws Exception {
        Field field = getClass().getSuperclass().getDeclaredField("processStarted");
        field.setAccessible(true);
        if ((boolean) field.get(this)) {
            destroyProcess();
            if (OS.isFamilyUnix())
                Runtime.getRuntime().exec(String.format("sudo pkill -P %d", getProcessId())).waitFor(10, TimeUnit.SECONDS);
        }
    }

}

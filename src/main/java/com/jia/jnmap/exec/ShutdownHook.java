package com.jia.jnmap.exec;


import java.util.ArrayList;
import java.util.List;

public class ShutdownHook extends Thread {

    private static final ShutdownHook instance = new ShutdownHook();

    static {
        Runtime.getRuntime().addShutdownHook(instance);
    }

    private List<Watchdog> watchdogs = new ArrayList<>();

    public ShutdownHook() {
        super("JNMAP-SHUTDOWN-HOOK");
    }

    public static ShutdownHook getInstance() {
        return instance;
    }

    public void addWatchdog(Watchdog watchdog) {
        watchdogs.add(watchdog);
    }

    public void removeWatchdog(Watchdog watchdog) {
        watchdogs.remove(watchdog);
    }

    @Override
    public void run() {
        watchdogs.parallelStream().forEach(watchdog -> {
            try {
                watchdog.destory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

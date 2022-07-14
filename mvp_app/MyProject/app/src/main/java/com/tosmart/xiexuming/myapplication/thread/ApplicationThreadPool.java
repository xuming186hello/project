package com.tosmart.xiexuming.myapplication.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiexuming
 */
public class ApplicationThreadPool {
    private static volatile ApplicationThreadPool instance;
    private final ThreadPoolExecutor appThreadPool;

    private ApplicationThreadPool() {
        appThreadPool = new ThreadPoolExecutor(
                3,
                6,
                500,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static ApplicationThreadPool getInstance() {
        if (instance == null) {
            synchronized (ApplicationThreadPool.class) {
                instance = new ApplicationThreadPool();
            }
        }
        return instance;
    }

    public ThreadPoolExecutor getThreadPool() {
        return appThreadPool;
    }
}

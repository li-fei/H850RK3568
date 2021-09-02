package com.yuneec.utils;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.paint.Color;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManage {

    private static ThreadPoolManage instance;

    public static ThreadPoolManage I() {
        if (instance == null) {
            instance = new ThreadPoolManage();
        }
        return instance;
    }

    public String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return df.format(new Date()) + " -> ";
    }

    private ScheduledExecutorService scheduledExecutorService;

    public void init() {
        if (scheduledExecutorService == null) {
            scheduledExecutorService = Executors.newScheduledThreadPool(128);
        }
        ADBUtils.getInstance().listener_cmd_adbDevices();
        startRunnable(commandTimeoutCheck, 500);
    }

    public void stop() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    public void startRunnable(Runnable runnable, long period) {
        if (period != 0) {
            scheduledExecutorService.scheduleAtFixedRate(runnable, 0, period, TimeUnit.MILLISECONDS);
        } else {
            scheduledExecutorService.submit(runnable);
        }
    }

    private Runnable commandTimeoutCheck = new Runnable() {
        @Override
        public void run() {
            SendPackage.I().checkTimeoutCommand();
        }
    };

    private class mRunnable implements Runnable {
        @Override
        public void run() {
        }
    }


}

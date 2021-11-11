package com.yuneec.command;

import com.yuneec.command.common.BaseCommand;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;

public class CommandContainer {

    private static CommandContainer instance;

    public static CommandContainer I() {
        if (instance == null) {
            instance = new CommandContainer();
        }
        return instance;
    }

    public ConcurrentHashMap<Integer, CommandListener> mCommandListenerList =
            new ConcurrentHashMap<Integer, CommandListener>();

    public ConcurrentHashMap<Integer, BaseCommand> mCommandList =
            new ConcurrentHashMap<Integer, BaseCommand>();

    public ConcurrentHashMap<Integer, ScheduledExecutorService> mCommandThreadPoolList =
            new ConcurrentHashMap<Integer, ScheduledExecutorService>();



}

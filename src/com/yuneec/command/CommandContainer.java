package com.yuneec.command;

import java.util.concurrent.ConcurrentHashMap;

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



}

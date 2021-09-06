package com.yuneec.utils;

import java.util.LinkedList;

public class Log {

    public static LinkedList logList = new LinkedList();

    public static void I(String info){
        System.out.println(ThreadPoolManage.I().getTime() + info);
        logList.add(ThreadPoolManage.I().getTime() + info);
        logList.add(1);
    }

    public static void E(String info){
        System.out.println(ThreadPoolManage.I().getTime() + info);
        logList.add(ThreadPoolManage.I().getTime() + info);
        logList.add(2);
    }

    public static void W(String info){
        System.out.println(ThreadPoolManage.I().getTime() + info);
        logList.add(ThreadPoolManage.I().getTime() + info);
        logList.add(3);
    }

    public static void V(String info){
        System.out.println(ThreadPoolManage.I().getTime() + info);
        logList.add(ThreadPoolManage.I().getTime() + info);
        logList.add(4);
    }

}

package com.yuneec.utils;

import java.util.ArrayList;
import java.util.LinkedList;

public class Log {

    public static LinkedList logList = new LinkedList();

    public static void I(String info){
        System.out.println(ThreadPoolManage.I().getTime() + info);
        logList.add(ThreadPoolManage.I().getTime() + info);
    }

}

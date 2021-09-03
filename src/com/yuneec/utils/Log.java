package com.yuneec.utils;

import java.util.ArrayList;

public class Log {

    public static ArrayList logList = new ArrayList();

    public static void I(String info){
        System.out.println(ThreadPoolManage.I().getTime() + info);
        logList.add(ThreadPoolManage.I().getTime() + info);
    }

}

package com.yuneec.utils;

public class Log {

    public static void I(String info){
        System.out.println(ThreadPoolManage.I().getTime() + info);
    }
}

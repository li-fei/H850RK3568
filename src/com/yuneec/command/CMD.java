package com.yuneec.command;

public class CMD {
    public static int CMD_ACK = 0;              /* 回复命令 pc<-->rk3568*/
    public static int CMD_TEST_FUNC = 1;        /* 功能测试 pc-->rk3568*/
    public static int CMD_TEST_FUNC_OK = 2;     /* 功能测试结果 pc<--rk3568 */
    public static int CMD_TEST_FUNC_ERR = 3;    /* 功能测试结果 pc<--rk3568 */
    public static int CMD_MAX_ID = 4;
}

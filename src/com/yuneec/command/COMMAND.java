package com.yuneec.command;

public class COMMAND {
    public static int CMD_ACK = 0;		            /* 回复命令 */
    public static int CMD_TEST_START = 1;           /* 开始测试 */
    public static int CMD_TEST_START_OK = 2;        /* 开始测试准备完毕 */
    public static int CMD_TEST_START_ERR = 3;       /* 开始测试准备失败 */

    public static int UART = 4;
    public static int WIFI = 5;
    public static int WLAN = 6;
}

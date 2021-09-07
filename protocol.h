#ifndef _PROTOCOL_H_
#define _PROTOCOL_H_

#include <stdint.h>
#include <stdbool.h>

#ifdef __cplusplus
extern "C" {
#endif

/* PCB板卡ID号 */
typedef enum {
	R2101_R=0,	/* h850 rk3568 board */
	R2101_F,	/* h850 io board */
	R2101_H,	/* h850 SD/HP board */
	BOARD_MAX_ID,
} YNC_BOARD;

/* PCB版本号 */
typedef struct {
	uint8_t r2101_r[2];
	uint8_t r2101_f[2];
	uint8_t r2101_h[2];
} ync_board_version_t;

/* 端子ID号 */
typedef enum {
	TERM_NULL=0,
	TERM_MAX_ID,
} YNC_BOARD_TERM;

/*	h850 rk3568 board : V30
	h850 io board : V20
	h850 SD/HP board : V10

	io board	J5	UART3_RX_GB1/UART3_TX_GB1
	rk board	J41	UART4_RX_GB2/UART4_TX_GB2
	rk board	J41	UART9_RX_EXTERNAL2/UART9_TX_EXTERNAL2
	io board	J3	RK3568_PWM4
	io board	J3	RK3568_GPIO3
	io board	J3	RK3568_GPIO4
	io board	J4	RK3568_PWM5
	io board	J4	RK3568_GPIO5
	io board	J4	RK3568_GPIO6
	io board	J5	ETH_100M
	rk board	J41	ETH_100M
	io board	J2	ETH_1000M
	rk board	J55	ETH_1000M
	rk board	J25	ARTOSYN_USB
	rk board	J53	WIFI_MODULE
	sd board	J1	SD_CARD
	sd board	J2	HEAD_PHONE
	rk board	J2500	USB3_0
	io board	J9	USB3_0
	rk board	J24	AUTOPILOP
*/

/* 功能ID号 */
typedef enum {
	FUNC_F_J5_UART3=0,
	FUNC_R_J41_UART4,
	FUNC_F_J12_UART9,
	FUNC_F_J3_PWM4,
	FUNC_F_J3_GPIO3,
	FUNC_F_J3_GPIO4,
	FUNC_F_J4_PWM5,
	FUNC_F_J4_GPIO5,
	FUNC_F_J4_GPIO6,
	FUNC_F_J4_GPIOALL,
	/* lan测试需要主机发送 data_cont数据为字符串形式的 iperf server ip */
	/* 测试成功，下位机发送data_cont数据为uint32_t类型的网速 */
	/* 测试失败，无附加数据 */
	FUNC_F_J5_ETH100,
	FUNC_R_J41_ETH100,
	FUNC_F_J2_ETH1000,
	FUNC_R_J55_ETH1000,
	FUNC_R_J25_ARTOSYN,
	/* 测试成功，下位机发送data_cont数据为uint32_t类型的扫描到的AP数量 */
	/* 测试失败，无附加数据 */
	FUNC_R_WIFI_MODULE,
	FUNC_H_SD_CARD,
	FUNC_H_HP,
	FUNC_R_J2500_USB30,
	FUNC_F_J9_USB30,
	FUNC_MAX_ID,
} YNC_TERM_FUNC;

/* 协议命令 */
typedef enum {
	CMD_ACK=0,		/* 回复命令 pc<-->rk3568*/
	CMD_TEST_FUNC,	/* 功能测试 pc-->rk3568*/
	CMD_TEST_FUNC_OK,   /* 功能测试结果 pc<--rk3568 */
	CMD_TEST_FUNC_ERR,  /* 功能测试结果 pc<--rk3568 */
	CMD_MAX_ID,
} YNC_CMD;

/*
protocol version : v1

上位机发送一条测试命令，下位机首先回复CMD_ACK命令，然后开始执行测试动作，测试动作结束后发送结果。
下位机为单线程解析数据包并执行，下位机有接收数据缓存，不会丢失命令。但是上位机连续发送命令，下位机可能不会及时回复

magic : 固定为 0xfe 0xef
pkg_len : 为整个数据包长度
pkg_id : 在接收消息端回复CMD_ACK命令时，pkg_id和接收到的数据包保持一致，消息发送端可用该pkg_id判断消息接收端是否收到消息
prot_ver : 固定为1
board_id : 上位机收发可忽略，下位机发送消息会有相应信息，见 YNC_BOARD 数据结构
board_ver : 上位机收发可忽略，下位机发送消息会有相应信息，见 ync_board_version_t 数据结构
term_id	: 固定为0，见 YNC_BOARD_TERM 数据结构
func_id	: 见 YNC_TERM_FUNC 数据结构，该为确定测试的具体端口与功能
cmd : 见 YNC_CMD 数据结构
data_len : 附件数据长度
data_cont : 如果有数据则直接填充数据，否则data_len后直接为crc数据
crc : crc16数据，忽略
*/
typedef struct {
	uint8_t magic[2];	/* 0xfe 0xef */
	uint8_t pkg_len[4]; /* length of the entire package, H byte -> L byte*/
	uint8_t pkg_id;		/* package id */
	uint8_t prot_ver;	/* protocol version, eg:1 */
	uint8_t board_id;	/* PCB板卡ID号, eg:R2101_R */
	uint8_t	board_ver[2];	/* PCB板卡版本号, eg:3.0 */
	uint8_t term_id;		/* 端子ID号, eg:J70 */
	uint8_t func_id;	/* 功能ID号, eg:uart8 */
	uint8_t	cmd;		/* 命令, eg:CMD_TEST_START */
	uint8_t	data_len[2];	/* 附加数据长度, H byte -> L byte */
	uint8_t	*data_cont;	/* 附加数据。如果有数据则直接填充数据，否则data_len后直接为crc数据, L byte -> H byte*/
	uint8_t crc[2];		/* crc of the entire package, H byte -> L byte */
} ync_packet_t;

#define MAGIC_H	0xfe
#define MAGIC_L 0xef
#define PROTOCOL_VER 1	/* 协议版本 */

#ifdef __cplusplus
}
#endif
#endif

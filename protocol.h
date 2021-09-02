#ifndef _PROTOCOL_H_
#define _PROTOCOL_H_

#include <stdint.h>
#include <stdbool.h>

#ifdef __cplusplus
extern "C" {
#endif

typedef enum {
	R2101_R=0,	/* h850 rk3568 board */
	R2101_F,	/* h850 io board */
	R2101_H,	/* h850 SD/HP board */
} YNC_BOARD;

typedef enum {
	J70=0,
} YNC_BOARD_TERM;

typedef enum {
	UART8=0,
} YNC_TERM_FUNC;

typedef enum {
	CMD_ACK=0,		/* 回复命令 */

	CMD_TEST_START, /* 开始测试 */
	CMD_TEST_START_OK, /* 开始测试准备完毕 */
	CMD_TEST_START_ERR, /* 开始测试准备失败 */

} YNC_CMD;

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
	uint8_t	*data_cont;	/* 附加数据 */
	uint8_t crc[2];		/* crc of the entire package, H byte -> L byte */
} ync_packet_t;

#ifdef __cplusplus
}
#endif
#endif
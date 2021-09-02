package com.yuneec.command.common;

import com.yuneec.command.BaseResponse;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class BaseCmd {

    byte[] command = new byte[18];

    public void init(){
        command[0] = (byte) 0xFE;
        command[1] = (byte) 0xEF;

        ByteBuffer buffer = ByteBuffer.wrap(command);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(2, command.length);

        command[6] = 0; //pkg_id
        command[7] = 0; //prot_ver
        command[8] = 0; //board_id
        buffer.putShort(9, (short) 1.0);// board_ver
        command[11] = 0; // term_id
        command[12] = 0; //func_id

        command[13] = (byte) getCommandId(); //cmd

        buffer.putShort(14, (short) 0); //data_len

        buffer.putShort(16, (short) 0); //crc

    }

    public abstract int getCommandId();


    public byte[] toRawData() {
        init();
        return command;
    }

    public abstract boolean needResend();

    public BaseResponse toResponse(byte[] data) {
        if (!needResend()) {
            return null;
        }
        BaseResponse res = new BaseResponse(getCommandId());
        res.trans(data);
        return res;
    }

    protected long lastUpdatedTime() {
        return lastUpdatedTime;
    }

    protected long lastUpdatedTime = System.currentTimeMillis();
    protected long addTimeOutTime = 0;

}

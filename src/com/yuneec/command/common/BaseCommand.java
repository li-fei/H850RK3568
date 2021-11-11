package com.yuneec.command.common;

import com.yuneec.command.BaseResponse;
import com.yuneec.utils.BytesUtils;
import com.yuneec.utils.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class BaseCommand {

    private int data_len;
    byte[] command;

    public void init(){
        data_len = getDataCont().length;
        command = new byte[18 + data_len];

        command[0] = (byte) 0xFE;
        command[1] = (byte) 0xEF;

        ByteBuffer buffer = ByteBuffer.wrap(command);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(2, command.length);

        command[6] = 0; //pkg_id
        command[7] = 1; //prot_ver
        command[8] = 0; //board_id
        buffer.putShort(9, (short) 1.0);// board_ver
        command[11] = 0; // term_id
        command[12] = (byte) getFuncId(); //func_id

        command[13] = (byte) getCommandId(); //cmd

        buffer.putShort(14, (short) data_len); //data_len

        System.arraycopy(getDataCont(),0,command,16,data_len);

        buffer.putShort(16+data_len , (short) 0); //crc

    }

    public abstract byte[] getDataCont();

    public abstract int getFuncId();

    public int getCommandId(){
        return 1;
    };

    public int getTimeOut(){
        return 1000 * 3;
    };

    public byte[] toRawData() {
        init();
//        Log.I("toRawData:" + BytesUtils.byteArrayToHexString(command, 0, command.length));
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

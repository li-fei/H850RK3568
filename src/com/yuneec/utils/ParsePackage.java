package com.yuneec.utils;

import com.yuneec.H850RK3568;
import com.yuneec.command.BaseResponse;
import com.yuneec.command.CommandContainer;
import com.yuneec.command.CommandListener;
import javafx.application.Platform;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ParsePackage {

    public final static int COMMAND_TIMEOUT = 3000;

    private static ParsePackage instance;

    public static ParsePackage I() {
        if (instance == null) {
            instance = new ParsePackage();
        }
        return instance;
    }

    public void analysis(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.BIG_ENDIAN);
        int length = buffer.getInt(2);
        int cmd = buffer.get(13) & 0x0FF;
        String data = BytesUtils.byteArrayToHexString(bytes, 0, length);
        Log.I("cmd: " + cmd + " receive: " + data);

        CommandListener listener = CommandContainer.I().mCommandListenerList.get(cmd);
        if (listener != null) {
            BaseResponse res = new BaseResponse(cmd);
            res.trans(bytes);
            listener.onSuccess(res);
            CommandContainer.I().mCommandListenerList.remove(cmd);
            if ((System.currentTimeMillis() - listener.getSendTimestamp()) > COMMAND_TIMEOUT) {
                listener.onTimeout();
                Log.I("cmd: " + cmd + " timeout");
            }
        }
    }


}

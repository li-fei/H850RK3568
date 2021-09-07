package com.yuneec.utils;

import com.yuneec.H850RK3568;
import com.yuneec.command.BaseResponse;
import com.yuneec.command.CMD;
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
        int funcID = buffer.get(12) & 0x0FF;
        int cmd = buffer.get(13) & 0x0FF;
        String data = BytesUtils.byteArrayToHexString(bytes, 0, length);
        if (cmd == CMD.CMD_ACK){
            Log.W("funcID: " + funcID + " receive: " + data);
            return;
        }
        Log.V("funcID: " + funcID + " receive: " + data);

        CommandListener listener = CommandContainer.I().mCommandListenerList.get(funcID);
        if (listener != null) {
            BaseResponse res = new BaseResponse(funcID);
            res.trans(bytes);
            listener.onSuccess(res);
            if ((System.currentTimeMillis() - listener.getSendTimestamp()) > COMMAND_TIMEOUT) {
                listener.onTimeout();
                Log.I("funcID: " + funcID + " timeout");
            }
            CommandContainer.I().mCommandListenerList.remove(funcID);
        }
    }


}

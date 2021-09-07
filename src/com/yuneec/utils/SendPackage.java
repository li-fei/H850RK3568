package com.yuneec.utils;

import com.yuneec.command.FUNC;
import com.yuneec.command.CommandContainer;
import com.yuneec.command.CommandListener;
import com.yuneec.command.common.CustomCommand;
import com.yuneec.command.common.TestStartCommand;
import com.yuneec.command.common.WifiCommand;

public class SendPackage {

    private Object listLock = new Object();
    private static SendPackage instance;

    public static SendPackage I() {
        if (instance == null) {
            instance = new SendPackage();
        }
        return instance;
    }

    public void sendCommand(int cmd) {
        sendCommand(cmd, 0);
    }

    public void sendCommand(int cmd, long period) {
        sendCommand(cmd, period, null);
    }

    public void sendCommand(int cmd, CommandListener listener) {
        sendCommand(cmd, 0, listener);
    }

    public void sendCommand(int cmd, long period, CommandListener listener) {
        byte[] bytes = null;
        if (cmd == FUNC.UART) {

        } else if (cmd == FUNC.WIFI) {
            bytes = new WifiCommand().toRawData();
        } else if (cmd == FUNC.CMD_TEST_START) {
            bytes = new TestStartCommand().toRawData();
        }else {
            bytes = new CustomCommand(cmd).toRawData();
        }
        SocketUtil.I().send(cmd, bytes, period, listener);
    }

    public void checkTimeoutCommand() {
        synchronized (listLock) {
            for (int key : CommandContainer.I().mCommandListenerList.keySet()) {
                CommandListener listener = CommandContainer.I().mCommandListenerList.get(key);
                if (listener != null && (System.currentTimeMillis() - listener.getSendTimestamp() > ParsePackage.COMMAND_TIMEOUT)) {
                    listener.onTimeout();
                    Log.E("checkTimeoutCommand command " + key + " timeout");
                    CommandContainer.I().mCommandListenerList.remove(key);
                }
            }
        }
    }


}

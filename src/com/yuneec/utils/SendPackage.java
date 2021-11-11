package com.yuneec.utils;

import com.yuneec.Global;
import com.yuneec.command.FUNC;
import com.yuneec.command.CommandContainer;
import com.yuneec.command.CommandListener;
import com.yuneec.command.common.*;

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
        BaseCommand command = null;
        if (cmd == FUNC.UART) {

        } else if (cmd == FUNC.WIFI) {
             command = new WifiCommand();
        } else if (cmd == FUNC.CMD_TEST_START) {
             command = new TestStartCommand();
        }else if (cmd == FUNC.ALL_TEST) {
             command = new AllTestCommand(Global.HostIP);
        }else {
             command = new CustomCommand(cmd);
        }
        SocketUtil.I().send(command, period, listener);
    }

    public void checkTimeoutCommand() {
        synchronized (listLock) {
            for (int key : CommandContainer.I().mCommandListenerList.keySet()) {
                CommandListener listener = CommandContainer.I().mCommandListenerList.get(key);
                BaseCommand command = CommandContainer.I().mCommandList.get(key);
                if (listener != null && (System.currentTimeMillis() - listener.getSendTimestamp() > command.getTimeOut())) {
                    listener.onTimeout();
                    Log.E("checkTimeoutCommand command " + key + " timeout");
                    CommandContainer.I().mCommandListenerList.remove(key);
                }
            }
        }
    }


}

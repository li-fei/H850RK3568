package com.yuneec.utils;

import com.yuneec.command.CommandContainer;
import com.yuneec.command.CommandListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Timer;
import java.util.TimerTask;

public class SocketUtil {
    private Socket socket;
    private String host = "127.0.0.1";
    private int SERVER_PORT = 6666;
    private static SocketUtil instance;

    public static SocketUtil I() {
        if (instance == null) {
            instance = new SocketUtil();
        }
        return instance;
    }

    public void listening() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                openSocket();
            }
        }, 2000);
    }

    private void openSocket() {
        Log.W("openSocket()...... ");
        try {
            socket = new Socket(host, SERVER_PORT);
            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[128];
            while (true) {
                is.read(bytes);
                parse(bytes);
            }
        } catch (IOException e) {
            ErrData.I().show("socket connect failed ...... ", true);
//			e.printStackTrace();
        }
    }

    private void sendBytes(byte[] bytes) {
        OutputStream out = null;
        try {
            out = socket.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("------> socket not connect ...... send data err ...");
            ErrData.I().show("socket not connect ...... send data err ...", true, 3000);
        }
    }

    public void parse(byte[] bytes) {
        ThreadPoolManage.I().startRunnable(new ParseRunnable(bytes));
    }

    private class ParseRunnable implements Runnable {
        byte[] bytes;

        ParseRunnable(byte[] bytes) {
            this.bytes = bytes;
        }

        @Override
        public void run() {
            ParsePackage.I().analysis(bytes);
        }
    }

    public void send(int cmd, byte[] bytes, long period, CommandListener listener) {
        ThreadPoolManage.I().startRunnable(new SendRunnable(bytes, listener), period, cmd);
    }


    private class SendRunnable implements Runnable {
        private byte[] bytes;
        private CommandListener listener;

        SendRunnable(byte[] bytes, CommandListener listener) {
            this.bytes = bytes;
            this.listener = listener;
        }

        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            buffer.order(ByteOrder.BIG_ENDIAN);
            int funcID = buffer.get(12) & 0x0FF;
            if (listener != null) {
                CommandContainer.I().mCommandListenerList.put(funcID, listener);
                listener.setSendTimeStamp(System.currentTimeMillis());
                listener.onStartSend();
            }
            Log.I("funcID: " + funcID + "  send:    " + BytesUtils.byteArrayToHexString(bytes, 0, bytes.length));
            sendBytes(bytes);
        }
    }
}

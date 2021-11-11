package com.yuneec.utils;

import com.yuneec.command.CommandContainer;
import com.yuneec.command.CommandListener;
import com.yuneec.command.common.BaseCommand;

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
    private InputStream inputStream;
    private String host = "127.0.0.1";
    private int SERVER_PORT = 6666;
    private static SocketUtil instance;
    private boolean isOpen = false;

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
            socket.setReuseAddress(true);
            inputStream = socket.getInputStream();
            isOpen = true;
            byte[] bytes = new byte[128];
            while (true) {
                inputStream.read(bytes);
                parse(bytes);
            }
        } catch (IOException e) {
            ErrData.I().show("socket connect failed ...... ", true);
//			e.printStackTrace();
        }
    }

    public void closeSocket(){
        if (socket != null){
            try {
                socket.close();
                inputStream.close();
                socket = null;
                isOpen = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        if (!isOpen){
            return;
        }
        String data = BytesUtils.byteArrayToHexString(bytes, 0, bytes.length);
        Log.W("parse : " + data);
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

    public void send(BaseCommand command, long period, CommandListener listener) {
        ThreadPoolManage.I().startRunnable(new SendRunnable(command, listener), period, command.getCommandId());
    }


    private class SendRunnable implements Runnable {
        private BaseCommand command;
        private CommandListener listener;

        SendRunnable(BaseCommand command, CommandListener listener) {
            this.command = command;
            this.listener = listener;
        }

        @Override
        public void run() {
            byte[] bytes = command.toRawData();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            buffer.order(ByteOrder.BIG_ENDIAN);
            int funcID = buffer.get(12) & 0x0FF;
            if (listener != null) {
                CommandContainer.I().mCommandListenerList.put(funcID, listener);
                listener.setSendTimeStamp(System.currentTimeMillis());
                listener.onStartSend();
                CommandContainer.I().mCommandList.put(funcID, command);
            }
            Log.I("funcID: " + funcID + "  send:    " + BytesUtils.byteArrayToHexString(bytes, 0, bytes.length));
            sendBytes(bytes);
        }
    }
}

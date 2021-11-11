package com.yuneec.views;


import com.yuneec.command.BaseResponse;
import com.yuneec.command.FUNC;
import com.yuneec.command.CommandListener;
import com.yuneec.utils.BytesUtils;
import com.yuneec.utils.Log;
import com.yuneec.utils.SendPackage;
import com.yuneec.utils.SocketUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Timer;
import java.util.TimerTask;

public class LeftViewController {

    private static LeftViewController instance;

    public static LeftViewController I() {
        if (instance == null) {
            instance = new LeftViewController();
        }
        return instance;
    }

    public void startAllTest() {
        sendCommand(FUNC.ALL_TEST);
    }

    public void startTestWifi() {
        RightViewController.I().setResult(LeftView.I().nodeList.get(1), RightViewController.TESTCODE.TESTING);
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            public void run() {
                RightViewController.I().setResult(LeftView.I().nodeList.get(1), RightViewController.TESTCODE.FAILED);
            }
        }, 3000);
    }

    public void setAllTestInfo(RightViewController.TESTCODE testcode){
        for (int i=0;i<LeftView.I().nodeList.size();i++){
            RightViewController.I().setResult(LeftView.I().nodeList.get(i), testcode);
        }
    }

    private void sendCommand(int cmd) {
        Log.I("sendCommand " + cmd);
        SendPackage.I().sendCommand(cmd, new CommandListener() {
            @Override
            public void onStartSend() {
                super.onStartSend();
                Log.I("onStartSend " + cmd);
                setAllTestInfo(RightViewController.TESTCODE.TESTING);
            }

            @Override
            public void onSuccess(BaseResponse response) {
                super.onSuccess(response);
                String data = BytesUtils.byteArrayToHexString(response.responseData, 0, response.responseData.length);
                Log.I("cmd: " + cmd + " onSuccess: " + data);
                byte[] rsData = response.responseData;
                ByteBuffer buffer = ByteBuffer.wrap(rsData);
                buffer.order(ByteOrder.BIG_ENDIAN);
                int data_len = buffer.getShort(14) & 0xFFFF;
                byte[] data_cont = new byte[data_len];
                System.arraycopy(rsData,16,data_cont,0,data_len);
                String data_cont_s = BytesUtils.byteArrayToHexString(data_cont, 0, data_len);
                Log.I("data_cont_s: " + data_cont_s);
                updateTestResult(data_cont);
                SocketUtil.I().closeSocket();
            }

            @Override
            public void onTimeout() {
                super.onTimeout();
                setAllTestInfo(RightViewController.TESTCODE.TIMEOUT);
            }
        });
    }

    private void updateTestResult(byte[] data_cont) {
        for (int i=0;i<data_cont.length/2;i++){
            int result = data_cont[i*2+1] & 0xFF;
            if (result == 1){
                RightViewController.I().setResult(LeftView.I().nodeList.get(i), RightViewController.TESTCODE.SUCCEED);
            }else {
                RightViewController.I().setResult(LeftView.I().nodeList.get(i), RightViewController.TESTCODE.FAILED);
            }
        }
    }


}

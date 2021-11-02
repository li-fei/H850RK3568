package com.yuneec.views;


import com.yuneec.command.BaseResponse;
import com.yuneec.command.FUNC;
import com.yuneec.command.CommandListener;
import com.yuneec.utils.BytesUtils;
import com.yuneec.utils.Log;
import com.yuneec.utils.SendPackage;

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

    public void start() {
        startTestUart();
        startTestWifi();
    }

    public void startTestUart() {
        RightViewController.I().setResult(LeftView.I().nodeList.get(0), RightViewController.TESTCODE.TESTING);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                RightViewController.I().setResult(LeftView.I().nodeList.get(0), RightViewController.TESTCODE.SUCCEED);
            }
        }, 5000);
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

    private void sendCommand(int cmd) {
        SendPackage.I().sendCommand(cmd, new CommandListener() {
            @Override
            public void onStartSend() {
                super.onStartSend();
                if (cmd == FUNC.WIFI) {
                    RightViewController.I().setResult(RightView.I().wifiNodesList, RightViewController.TESTCODE.TESTING);
                }
            }

            @Override
            public void onSuccess(BaseResponse response) {
                super.onSuccess(response);
                String data = BytesUtils.byteArrayToHexString(response.responseData, 0, response.responseData.length);
                Log.I("cmd: " + cmd + " onSuccess: " + data);
                if (cmd == FUNC.WIFI) {
                    RightViewController.I().setResult(RightView.I().wifiNodesList, RightViewController.TESTCODE.SUCCEED);
                }
            }

            @Override
            public void onTimeout() {
                super.onTimeout();
                if (cmd == FUNC.WIFI) {
                    RightViewController.I().setResult(RightView.I().wifiNodesList, RightViewController.TESTCODE.TIMEOUT);
                }
            }
        });
    }


}

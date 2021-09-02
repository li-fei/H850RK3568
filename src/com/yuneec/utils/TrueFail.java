package com.yuneec.utils;

import com.yuneec.command.COMMAND;
import com.yuneec.views.RightView;
import com.yuneec.views.RightViewController;

public class TrueFail {

    private static TrueFail instance;

    public static TrueFail I() {
        if (instance == null) {
            instance = new TrueFail();
        }
        return instance;
    }

    public void update(boolean flag, int cmd) {
        if (cmd == COMMAND.UART) {
            if (flag) {
                RightViewController.I().setResult(RightView.I().uartNodesList, RightViewController.TESTCODE.SUCCEED);
            } else {
                RightViewController.I().setResult(RightView.I().uartNodesList, RightViewController.TESTCODE.FAILED);
            }
        } else if (cmd == COMMAND.WIFI) {
            if (flag) {
                RightViewController.I().setResult(RightView.I().wifiNodesList, RightViewController.TESTCODE.SUCCEED);
            } else {
                RightViewController.I().setResult(RightView.I().wifiNodesList, RightViewController.TESTCODE.FAILED);
            }
        }
    }


}

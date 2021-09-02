package com.yuneec.views;


import com.yuneec.command.BaseResponse;
import com.yuneec.command.COMMAND;
import com.yuneec.command.CommandListener;
import com.yuneec.utils.BytesUtils;
import com.yuneec.utils.Log;
import com.yuneec.utils.SendPackage;
import com.yuneec.utils.ThreadPoolManage;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class RightViewController {

    private static RightViewController instance;

    public static RightViewController I() {
        if (instance == null) {
            instance = new RightViewController();
        }
        return instance;
    }

    public void sendCommand(int cmd) {
        SendPackage.I().sendCommand(cmd, 4000, new CommandListener() {
            @Override
            public void onStartSend() {
                super.onStartSend();
                if (cmd == COMMAND.WIFI) {
                    setResult(RightView.I().wifiNodesList, TESTCODE.TESTING);
                }
            }

            @Override
            public void onSuccess(BaseResponse response) {
                super.onSuccess(response);
                String data = BytesUtils.byteArrayToHexString(response.responseData, 0, response.responseData.length);
                Log.I("cmd: " + cmd + " onSuccess: " + data);
                if (cmd == COMMAND.WIFI) {
                    setResult(RightView.I().wifiNodesList, TESTCODE.SUCCEED);
                }
            }

            @Override
            public void onTimeout() {
                super.onTimeout();
                if (cmd == COMMAND.WIFI) {
                    setResult(RightView.I().wifiNodesList, TESTCODE.TIMEOUT);
                }
            }
        });
    }

    public static enum TESTCODE {
        NOTEST, TESTING, SUCCEED, FAILED, TIMEOUT
    }

    public void setResult(ArrayList list, TESTCODE result) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switch (result) {
                    case TESTING:
                        ((Label) list.get(1)).setTextFill(Color.YELLOW);
                        ((Label) list.get(1)).setText("正在测试");
                        ((ProgressIndicator) list.get(2)).setOpacity(1);
                        break;
                    case SUCCEED:
                        ((Label) list.get(1)).setTextFill(Color.GREEN);
                        ((Label) list.get(1)).setText("测试成功");
                        ((ProgressIndicator) list.get(2)).setOpacity(0);
                        break;
                    case FAILED:
                        ((Label) list.get(1)).setTextFill(Color.RED);
                        ((Label) list.get(1)).setText("测试失败");
                        ((ProgressIndicator) list.get(2)).setOpacity(0);
                        break;
                    case TIMEOUT:
                        ((Label) list.get(1)).setTextFill(Color.RED);
                        ((Label) list.get(1)).setText("超时失败");
                        ((ProgressIndicator) list.get(2)).setOpacity(0);
                        break;
                }
            }
        });
    }


}

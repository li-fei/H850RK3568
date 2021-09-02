package com.yuneec.utils;

import com.yuneec.H850RK3568;
import com.yuneec.views.RightView;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Timer;
import java.util.TimerTask;

public class ErrData {

    private static ErrData instance;

    public static ErrData I() {
        if (instance == null) {
            instance = new ErrData();
        }
        return instance;
    }

    public void show(String info,boolean red) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                H850RK3568.errData.setText(info);
                if (red){
                    H850RK3568.errData.setTextFill(Color.RED);
                } else {
                    H850RK3568.errData.setTextFill(Color.GREEN);
                }
            }
        });
    }

    public void show(String info,boolean color,int time) {
        show(info,color);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                show("",color);
            }
        }, time);
    }
}

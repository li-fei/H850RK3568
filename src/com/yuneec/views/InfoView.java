package com.yuneec.views;


import com.yuneec.Configs;
import com.yuneec.H850RK3568;
import com.yuneec.utils.ADBUtils;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class InfoView {
    private static InfoView instance;

    public static InfoView I() {
        if (instance == null) {
            instance = new InfoView();
        }
        return instance;
    }

    public Label errData() {
        Label errData = new Label("");
        errData.setTextFill(Color.web(Configs.red_color));
        errData.setPrefWidth(500);
        errData.setFont(Font.font(16));
        errData.setTranslateX(90);
        errData.setTranslateY(8);
        return errData;
    }

    public Button logButton() {
        Button logButton = YButton.getInstance().initButton(null, "Log", new YButton.OnClickListener() {
            @Override
            public void onLeftClick() {
                new LogView().start(new Stage());
            }
        });
        logButton.setTranslateX(30);
        logButton.setTranslateY(10);
        logButton.setPrefHeight(25);
        logButton.setPrefWidth(40);
        return logButton;
    }

    public Label usbStatus() {
        Label labelusbStatus = new Label("USB未连接");
        labelusbStatus.setTextFill(Color.web(Configs.white_color));
        labelusbStatus.setFont(Font.font(16));
        labelusbStatus.setPrefWidth(120);
        labelusbStatus.setTranslateX(30);
        labelusbStatus.setTranslateY(8);
        return labelusbStatus;
    }

    public Label socketStatus() {
        Label label = new Label("通信未连接");
        label.setTextFill(Color.web(Configs.red_color));
        label.setFont(Font.font(16));
        label.setPrefWidth(130);
        label.setTranslateX(30);
        label.setTranslateY(8);
        return label;
    }

    public void updateSocketStatus(String info,String color){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                H850RK3568.labelSocketStatus.setText(info);
                H850RK3568.labelSocketStatus.setTextFill(Color.web(color));
            }
        });
    }

    public ProgressIndicator socketPro() {
        ProgressIndicator pb = new ProgressIndicator();
        pb.setPrefWidth(50);
        pb.setMaxHeight(30);
        pb.setTranslateX(20);
        pb.setTranslateY(10);
        pb.setOpacity(0);
        return pb;
    }
}

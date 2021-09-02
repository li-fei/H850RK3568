package com.yuneec.views;


import com.yuneec.Configs;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class LeftView {
    private static LeftView instance;

    public static LeftView I() {
        if (instance == null) {
            instance = new LeftView();
        }
        return instance;
    }

    VBox vBox;
    ArrayList uartNodesList,wifiNodesList,wlanNodesList;

    public VBox init(){
        vBox = new VBox();
        vBox.setPrefWidth(400);
        vBox.setPrefHeight(500);
        vBox.setTranslateX(30);
        vBox.setTranslateY(20);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(5);
        vBox.setBackground(new Background(new BackgroundFill(Color.web(Configs.backgroundColor), new CornerRadii(5), new Insets(1))));
        vBox.setBorder(new Border(new BorderStroke(Paint.valueOf(Configs.blue_color),BorderStrokeStyle.SOLID,new CornerRadii(8),new BorderWidths(1.5))));

        uartNodesList = getHBox("串口",true);
        wifiNodesList = getHBox("WIFI",false);
        wlanNodesList = getHBox("WLAN",false);

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        Y.i().setResult(wifiNodesList,Y.TESTCODE.SUCCEED);
//                    }
//                });
//            }
//        }, 5000);
        return vBox;
    }

    private ArrayList getHBox(String name, boolean result) {
        HBox hBox1 = new HBox();
        hBox1.setPrefWidth(300);
        hBox1.setPrefHeight(40);
        hBox1.setPadding(new Insets(5,5,5,5));

        Label text1 = new Label(name + " : ");
        text1.setPrefWidth(200);
        text1.setTranslateX(20);
        text1.setTextFill(Color.web(Configs.white_color));
        text1.setFont(Font.font(14));
        hBox1.getChildren().add(text1);

        Label resultText = new Label("未测试");
        resultText.setPrefWidth(70);
        resultText.setFont(Font.font(14));
        resultText.setTextFill(Color.web(Configs.white_color));
        hBox1.getChildren().add(resultText);

        ProgressIndicator pb = new ProgressIndicator();
        pb.setOpacity(0);
        hBox1.getChildren().add(pb);

        hBox1.setAlignment(Pos.CENTER_LEFT);
        hBox1.setBorder(new Border(new BorderStroke(Paint.valueOf(Configs.grey_color),BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(0.5))));

        vBox.getChildren().add(hBox1);
//        Line line = new Line(10, 10, 380, 10);
//        line.setStroke(Color.WHITE);
//        vBox.getChildren().add(line);
        ArrayList list = new ArrayList();
        list.add(hBox1);
        list.add(resultText);
        list.add(pb);
        return list;
    }


}

package com.yuneec.views;


import com.yuneec.Configs;
import com.yuneec.command.COMMAND;
import com.yuneec.utils.TrueFail;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class RightView {
    private static RightView instance;
    public ArrayList uartNodesList;
    public ArrayList wifiNodesList;

    public static RightView I() {
        if (instance == null) {
            instance = new RightView();
        }
        return instance;
    }

    VBox vBox;
    public VBox init(){
        vBox = new VBox();
        vBox.setPrefWidth(500);
        vBox.setPrefHeight(500);
        vBox.setTranslateX(60);
        vBox.setTranslateY(20);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(5);
        vBox.setBackground(new Background(new BackgroundFill(Color.web(Configs.backgroundColor), new CornerRadii(5), new Insets(1))));
        vBox.setBorder(new Border(new BorderStroke(Paint.valueOf(Configs.blue_color),BorderStrokeStyle.SOLID,new CornerRadii(8),new BorderWidths(1.5))));
        initView();
        return vBox;
    }

    private void initView() {
        uartNodesList = getHBox("串口", COMMAND.UART,false);
        wifiNodesList = getHBox("WIFI",COMMAND.WIFI,true);
    }

    private ArrayList getHBox(String name, int CMD, boolean showTrueFailBtn) {
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
        pb.setPrefWidth(50);
        pb.setMaxHeight(25);
        pb.setOpacity(0);
//        pb.setStyle(" -fx-progress-color: #d9d6c3;");
        hBox1.getChildren().add(pb);

        YButton.getInstance().setButtonWidth(50);
        Button startTest = YButton.getInstance().initButton(null, "测试", new YButton.OnClickListener() {
            @Override
            public void onLeftClick() {
                RightViewController.I().sendCommand(CMD);
            }
        });
        startTest.setPrefHeight(23);
        startTest.setTranslateX(10);
        hBox1.getChildren().add(startTest);

        Button trueButtonTest = YButton.getInstance().initButton("image/true.png", "image/true_press.png","", new YButton.OnClickListener() {
            @Override
            public void onLeftClick() {
                TrueFail.I().update(true,CMD);
            }
        });
        trueButtonTest.setPrefWidth(20);
        trueButtonTest.setPrefHeight(20);
        trueButtonTest.setTranslateX(30);
        trueButtonTest.setBorder(null);

        Button falseButtonTest = YButton.getInstance().initButton("image/fail.png","image/fail_press.png", "", new YButton.OnClickListener() {
            @Override
            public void onLeftClick() {
                TrueFail.I().update(false,CMD);
            }
        });
        falseButtonTest.setPrefWidth(20);
        falseButtonTest.setPrefHeight(20);
        falseButtonTest.setTranslateX(40);
        falseButtonTest.setBorder(null);

        if (showTrueFailBtn){
            hBox1.getChildren().addAll(trueButtonTest,falseButtonTest);
        }

        hBox1.setAlignment(Pos.CENTER_LEFT);
        hBox1.setBorder(new Border(new BorderStroke(Paint.valueOf(Configs.grey_color),BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(0.5))));
        vBox.getChildren().add(hBox1);

        ArrayList list = new ArrayList();
        list.add(hBox1);
        list.add(resultText);
        list.add(pb);
        list.add(CMD);

        return list;
    }


}

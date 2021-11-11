package com.yuneec;

import com.yuneec.utils.ADBUtils;
import com.yuneec.utils.Log;
import com.yuneec.utils.ThreadPoolManage;
import com.yuneec.utils.Utils;
import com.yuneec.views.InfoView;
import com.yuneec.views.LeftView;
import com.yuneec.views.RightView;
import com.yuneec.views.YButton;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class H850RK3568 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static Label labelusbStatus;
    public static Label errData;
    public static Label labelSocketStatus;
    public static ProgressIndicator proSocket;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(" Yuneec H850RK3568  ( Version:1.0.0 )");
        BorderPane root = new BorderPane();
        VBox vBox = new VBox();

        labelusbStatus = InfoView.I().usbStatus();
        labelSocketStatus = InfoView.I().socketStatus();
        proSocket = InfoView.I().socketPro();
        Button startTest = YButton.getInstance().initButton(null, "开始测试", new YButton.OnClickListener() {
            @Override
            public void onLeftClick() {
                ADBUtils.getInstance().startTest();
            }
        });
        startTest.setTranslateX(40);
        startTest.setTranslateY(10);
        startTest.setPrefHeight(30);

        Button logBtn = YButton.getInstance().initButton(null, "log", new YButton.OnClickListener() {
            @Override
            public void onLeftClick() {
                ADBUtils.getInstance().saveLog();
            }
        });
        logBtn.setPrefWidth(30);
        logBtn.setTranslateX(40);
        logBtn.setTranslateY(50);
        logBtn.setPrefHeight(30);

        errData = InfoView.I().errData();
        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(40);
        hBox1.setPadding(new Insets(5, 5, 5, 5));
        hBox1.getChildren().addAll(labelusbStatus, labelSocketStatus, proSocket, startTest
//        ,errData,InfoView.I().logButton()
        );
        hBox1.setAlignment(Pos.CENTER_LEFT);

        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(LeftView.I().init());

        vBox.getChildren().addAll(hBox1, hBox2);

        vBox.getChildren().add(logBtn);

        root.setCenter(vBox);
        root.setBackground(new Background(new BackgroundFill(Color.web(Configs.backgroundColor), null, null)));
        Scene scene = new Scene(root, Configs.SceneWidth, Configs.SceneHeight);
        primaryStage.setScene(scene);
//		primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("image/launcher.png"));
        primaryStage.show();
        Global.HostIP = Utils.getHostIP();
        Log.I("HostIP:" + Global.HostIP);

        ThreadPoolManage.I().init();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
//				event.consume();
                ThreadPoolManage.I().stop();
                System.exit(0);
            }
        });

        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double width = primaryStage.getWidth();
//				YLog.I("widthProperty oldValue:" + oldValue + " ,newValue:" + newValue + " ,width:" + width);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        vBox.setTranslateX((width - Configs.SceneWidth) / 2);
                    }
                });
            }
        });
        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double height = primaryStage.getHeight();
                vBox.setTranslateY((height - Configs.SceneHeight) / 2);
            }
        });

    }

}

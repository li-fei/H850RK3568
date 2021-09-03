package com.yuneec.views;

import com.yuneec.Configs;
import com.yuneec.Global;
import com.yuneec.utils.Log;
import com.yuneec.utils.SendPackage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestViewStage extends Application {
    double screenWidth,screenHeight;
    double windowWidth = 500;
    double windowHeight = 500;
    ScrollPane rootPane;
    VBox vBox;

    private static TestViewStage instance;

    public static TestViewStage I() {
        if (instance == null) {
            instance = new TestViewStage();
        }
        return instance;
    }

    public void start(Stage primaryStage) {
        rootPane = new ScrollPane();
        vBox = new VBox();
        vBox.setPadding(new Insets(5,10,5,10));
//        vBox.setMaxHeight(windowHeight - 20);
        Scene scene = new Scene(rootPane, windowWidth, windowHeight);
        rootPane.setContent(vBox);
        rootPane.setFitToHeight(true);
        rootPane.setFitToWidth(true);
        vBox.setBackground(new Background(new BackgroundFill(Color.web(Configs.backgroundColor), null, null)));
        primaryStage.setScene(scene);
        Screen screen = Screen.getPrimary();
        Rectangle2D rectangle2D = screen.getBounds();
        screenWidth = rectangle2D.getWidth();
        screenHeight = rectangle2D.getHeight();
        primaryStage.setX(screenWidth/2-windowWidth/2);
        primaryStage.setY(screenHeight/2-Configs.SceneHeight/2);
//        primaryStage.resizableProperty().setValue(false);
//        primaryStage.setAlwaysOnTop(true);
//        primaryStage.setX();
        primaryStage.setTitle("TestViewStage");
        primaryStage.show();

        initTest();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

            }
        });

    }

    private void initTest() {
        getHBox("发送的命令ID",true);
    }

    private ArrayList getHBox(String name, boolean result) {
        HBox hBox1 = new HBox();
        hBox1.setPrefWidth(300);
        hBox1.setPrefHeight(40);
        hBox1.setPadding(new Insets(5,5,5,5));

        Label text1 = new Label(name + " : ");
        text1.setPrefWidth(150);
        text1.setTranslateX(1);
        text1.setTextFill(Color.web(Configs.white_color));
        text1.setFont(Font.font(14));
        hBox1.getChildren().add(text1);

        TextField field = new TextField();
        field.setPrefSize(80, 25);
        field.setEditable(true);
        field.setAlignment(Pos.CENTER_LEFT);
        hBox1.getChildren().add(field);
        hBox1.setMargin(text1,new Insets(0, 0, 0, 30));
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length()>5){
                    field.setText(newValue.substring(0,5));
                }
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        YButton.getInstance().setButtonWidth(50);
        Button startTest = YButton.getInstance().initButton(null, "SEND", new YButton.OnClickListener() {
            @Override
            public void onLeftClick() {
                SendPackage.I().sendCommand(Integer.parseInt(field.getText()));
            }
        });
        startTest.setPrefHeight(23);
        startTest.setTranslateX(130);
        hBox1.getChildren().add(startTest);

        hBox1.setAlignment(Pos.CENTER_LEFT);
        hBox1.setBorder(new Border(new BorderStroke(Paint.valueOf(Configs.grey_color),BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(0.5))));

        vBox.getChildren().add(hBox1);
//        Line line = new Line(10, 10, 380, 10);
//        line.setStroke(Color.WHITE);
//        vBox.getChildren().add(line);
        ArrayList list = new ArrayList();
        list.add(hBox1);
        return list;
    }




    public static void main(String[] args) {
        launch(args);
    }


}

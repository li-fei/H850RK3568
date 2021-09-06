package com.yuneec.views;

import com.yuneec.Configs;
import com.yuneec.Global;
import com.yuneec.utils.Log;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LogView extends Application {
    double screenWidth,screenHeight;
    double windowWidth = 800;
    double windowHeight = 500;
    ScrollPane rootPane;
    VBox vBox;

    private static LogView instance;
    public Stage primaryStage;

    public static LogView I() {
        if (instance == null) {
            instance = new LogView();
        }
        return instance;
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
        primaryStage.setTitle("Log");
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Global.LogViewIsShowing = false;
                stop();
            }
        });

        showLog();
    }

    private ScheduledExecutorService scheduledExecutorService;

    public void showLog() {
        if (scheduledExecutorService == null) {
            scheduledExecutorService = Executors.newScheduledThreadPool(128);
        }
        scheduledExecutorService.scheduleAtFixedRate(showLogRunnable, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }

    private Runnable showLogRunnable = new Runnable() {
        @Override
        public void run() {
            int len = Log.logList.size();
            if (len > 0){
                String info = (String) Log.logList.poll();
                int color = (int) Log.logList.poll();
                setText(info,color);
            }
        }
    };

    public void setText(String info,int color){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Text testData = new Text(info);
                if (color == 1){
                    testData.setFill(Color.web(Configs.grey_color));
                }else if (color == 2){
                    testData.setFill(Color.web(Configs.red_color));
                }else if (color == 3){
                    testData.setFill(Color.web(Configs.yellow_color));
                }else if (color == 4){
                    testData.setFill(Color.web(Configs.green_color));
                }
                testData.setFont(Font.font(16));
                vBox.getChildren().add(testData);
                slowScrollToBottom(rootPane);
            }
        });
    }

    static void slowScrollToBottom(ScrollPane scrollPane) {
        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(scrollPane.vvalueProperty(), 1)));
        animation.play();
    }


    public static void main(String[] args) {
        launch(args);
    }


}

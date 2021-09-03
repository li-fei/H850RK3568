package com.yuneec.views;

import com.yuneec.Configs;
import com.yuneec.utils.Log;
import com.yuneec.utils.SendPackage;
import com.yuneec.utils.ThreadPoolManage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LogView extends Application {
    double screenWidth,screenHeight;
    double windowWidth = 800;
    double windowHeight = 500;
    ScrollPane rootPane;
    VBox vBox;

    private static LogView instance;

    public static LogView I() {
        if (instance == null) {
            instance = new LogView();
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
        primaryStage.setTitle("Log");
        primaryStage.show();

        showLog();
    }

    private void showLog() {
        ThreadPoolManage.I().startRunnable(showLogRunnable,100);
    }

    private Runnable showLogRunnable = new Runnable() {
        @Override
        public void run() {
            int len = Log.logList.size();
            if (len > 0){
                String info = (String) Log.logList.get(len-1);
                setText(info);
                Log.logList.remove(len-1);
            }
        }
    };

    public void setText(String info){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Text testData = new Text(info);
                testData.setFill(Color.web(Configs.grey_color));
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

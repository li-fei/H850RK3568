package com.yuneec.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class YuneecFX extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(" Yuneec ");
		BorderPane root = new BorderPane();
		HBox hBox = new HBox();
//		Global.hBox.setPadding(new Insets(Configs.Spacing, 0, Configs.Spacing, 0));
//		Global.hBox.setSpacing(1);
		root.setCenter(hBox);
		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
//		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("image/launcher.png"));
		primaryStage.show();


	}

}

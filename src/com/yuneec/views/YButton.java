package com.yuneec.views;

import com.yuneec.Configs;
import com.yuneec.utils.Utils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public class YButton {

    private static YButton instance;
    public Background centerSettingButtonUnclickBackground = new Background(
            new BackgroundFill(Paint.valueOf(Configs.lightGray_color), new CornerRadii(5), new Insets(1)));
    public Background centerSettingButtonClickBackground = new Background(
            new BackgroundFill(Paint.valueOf(Configs.backgroundColor), new CornerRadii(5), new Insets(1)));
    private OnClickListener onClickListener;
    public interface OnClickListener{
        void onLeftClick();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static YButton getInstance() {
        if (instance == null) {
            instance = new YButton();
        }
        return instance;
    }


    public void setButtonWidth(int buttonWidth) {
        this.buttonWidth = buttonWidth;
    }

    private int buttonWidth = 80;

    public void setButtonRadii(int buttonRadii) {
        this.buttonRadii = buttonRadii;
    }

    private int buttonRadii = 5;

    public Border getBorder(int buttonRadii,String color){
        return new Border(new BorderStroke(Paint.valueOf(color), BorderStrokeStyle.SOLID,new CornerRadii(buttonRadii),new BorderWidths(1.0)));
    }

    public Button initButton(String imagePath,String text,OnClickListener onClickListener) {
        return initButton(imagePath,null,text,onClickListener);
    }

    public Button initButton(String imagePath,String pressImagePath,String text,OnClickListener onClickListener) {
        Button button = new Button();
        button.setText(text);
//        button.setFont(Font.font(12));
        button.setPrefWidth(buttonWidth);
        button.setPadding(new Insets(0,0,0,0));
        button.setTextFill(Paint.valueOf(Configs.grey_color));
        ImageView imageView = null;
        if(imagePath != null){
            imageView = new ImageView(new Image(imagePath));
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            button.setGraphic(imageView);
        }
        button.setBackground(centerSettingButtonUnclickBackground);
        Border border = new Border(new BorderStroke(Paint.valueOf(Configs.blue_color), BorderStrokeStyle.SOLID,new CornerRadii(buttonRadii),new BorderWidths(1.0)));
        button.setBorder(border);

        ImageView finalImageView = imageView;
        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (Utils.mouseLeftClick(e)) {
                    button.setBackground(centerSettingButtonClickBackground);
                    if (pressImagePath != null){
                        finalImageView.setImage(new Image(pressImagePath));
                    }
                }
            }
        });

        button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(imagePath != null){
                    finalImageView.setImage(new Image(imagePath));
                }
                button.setBackground(centerSettingButtonUnclickBackground);
                if (onClickListener != null){
                    onClickListener.onLeftClick();
                }
            }
        });
        return button;
    }

    public Button creatSettingButton(String imagePath,String text) {
        Button button = new Button();
        button.setText(text);
        button.setTranslateX(10);
        button.setTranslateY(5);
        button.setPrefWidth(80);
        button.setTextFill(Paint.valueOf(Configs.grey_color));
        if(imagePath != null){
            ImageView imageView = new ImageView(new Image(imagePath));
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            button.setGraphic(imageView);
        }
        button.setBackground(centerSettingButtonUnclickBackground);
        Border border = new Border(new BorderStroke(Paint.valueOf(Configs.blue_color),BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(1.5)));
        button.setBorder(border);
        return button;
    }
}
